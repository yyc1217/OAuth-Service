package tw.edu.ncu.cc.oauth.server.service.oauth

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest
import org.apache.oltu.oauth2.common.error.OAuthError
import org.apache.oltu.oauth2.common.exception.OAuthProblemException
import org.apache.oltu.oauth2.common.exception.OAuthSystemException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.oauth.server.domain.AccessToken
import tw.edu.ncu.cc.oauth.server.helper.StringHelper
import tw.edu.ncu.cc.oauth.server.helper.TimeBuilder
import tw.edu.ncu.cc.oauth.server.helper.data.TimeUnit
import tw.edu.ncu.cc.oauth.server.service.common.LogService
import tw.edu.ncu.cc.oauth.server.service.domain.AccessTokenService
import tw.edu.ncu.cc.oauth.server.service.domain.ClientService
import tw.edu.ncu.cc.oauth.server.service.domain.RefreshTokenService

import javax.servlet.http.HttpServletResponse

@Service( "RefreshTokenExchangeService" )
class RefreshTokenExchangeService implements TokenExchangeService {

    @Autowired
    def LogService logService

    @Autowired
    def ClientService clientService;

    @Autowired
    def RefreshTokenService refreshTokenService;

    @Autowired
    def AccessTokenService accessTokenService;

    @Override
    String buildResonseMessage( OAuthTokenRequest request, long expireSeconds ) throws OAuthProblemException, OAuthSystemException {

        validateOauthRequest( request );

        AccessToken accessToken = prepareAccessToken( request, expireSeconds );

        return buildResponseMessage( accessToken.token, expireSeconds );
    }

    private void validateOauthRequest( OAuthTokenRequest request ) {

        String clientID     = request.getClientId();
        String clientSecret = request.getClientSecret();
        String refreshToken = request.getRefreshToken();

        logService.info(
                "EXCHANGE REFRESHTOKEN",
                "CLIENT:" + clientID,
                "REFRESHTOKEN[10]:" + StringHelper.first( refreshToken, 10 )
        )

        if ( ! clientService.isCredentialValid( clientID, clientSecret ) ) {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.INVALID_CLIENT, "INVALID CLIENT"
            );
        }

        if( ! refreshTokenService.isTokenUnexpiredWithClientId( refreshToken , clientID ) ) {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.INVALID_GRANT, "INVALID REFRESH TOKEN"
            );
        }
    }

    private AccessToken prepareAccessToken( OAuthTokenRequest request, long expireSeconds ) {
        accessTokenService.createByRefreshToken(
                new AccessToken(
                        dateExpired: dicideExpireDate( expireSeconds )
                ),
                refreshTokenService.readUnexpiredByRealToken( request.getRefreshToken(), [ 'client', 'scope', 'user', 'accessToken' ] )
        )
    }

    private static Date dicideExpireDate( long expireSeconds ) {
        if( expireSeconds <= 0 ) {
            return TimeBuilder.now().after( 30, TimeUnit.DAY ).buildDate()
        } else {
            return TimeBuilder.now().after( expireSeconds, TimeUnit.SECOND ).buildDate()
        }
    }

    private static String buildResponseMessage( String token, long expireSeconds ) {
        return org.apache.oltu.oauth2.as.response.OAuthASResponse
                .tokenResponse( HttpServletResponse.SC_OK )
                .setAccessToken(  token )
                .setTokenType( "Bearer" )
                .setExpiresIn( expireSeconds as String )
                .buildJSONMessage()
                .getBody();
    }

}