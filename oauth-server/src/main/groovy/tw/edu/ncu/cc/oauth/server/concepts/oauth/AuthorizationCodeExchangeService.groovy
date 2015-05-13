package tw.edu.ncu.cc.oauth.server.concepts.oauth

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest
import org.apache.oltu.oauth2.common.error.OAuthError
import org.apache.oltu.oauth2.common.exception.OAuthProblemException
import org.apache.oltu.oauth2.common.exception.OAuthSystemException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessToken
import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessTokenService
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCodeService
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCode_
import tw.edu.ncu.cc.oauth.server.concepts.client.ClientService
import tw.edu.ncu.cc.oauth.server.concepts.log.LogService
import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshToken
import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshTokenService
import tw.edu.ncu.cc.oauth.server.helper.StringHelper
import tw.edu.ncu.cc.oauth.server.helper.TimeBuilder
import tw.edu.ncu.cc.oauth.server.helper.data.TimeUnit

import javax.servlet.http.HttpServletResponse

@Service( "AuthCodeExchangeService" )
class AuthorizationCodeExchangeService implements TokenExchangeService {

    @Autowired
    def LogService logService

    @Autowired
    def ClientService clientService

    @Autowired
    def AccessTokenService accessTokenService

    @Autowired
    def RefreshTokenService refreshTokenService

    @Autowired
    def AuthorizationCodeService authCodeService

    @Override
    @Transactional
    String buildResonseMessage( OAuthTokenRequest request, long expireSeconds ) throws OAuthProblemException, OAuthSystemException {

        validateOauthRequest( request )

        AccessToken accessToken   = prepareAccessToken( request, expireSeconds )
        RefreshToken refreshToken = prepareRefreshToken( accessToken )

        return buildResponseMessage( accessToken.encryptedToken, refreshToken.encryptedToken, expireSeconds )
    }

    private void validateOauthRequest( OAuthTokenRequest request ) {

        String clientID     = request.getClientId()
        String clientSecret = request.getClientSecret()
        String authCode     = request.getCode()

        logService.info(
                "EXCHANGE AUTHCODE",
                "CLIENT:" + clientID,
                "CODE[10]:" + StringHelper.first( authCode, 10 )
        )

        if ( ! clientService.isCredentialValid( clientID, clientSecret ) ) {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.INVALID_CLIENT, "INVALID CLIENT"
            )
        }

        if( ! authCodeService.isUnexpiredCodeMatchesClientId( authCode, clientID ) ) {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.INVALID_GRANT, "INVALID AUTH CODE"
            )
        }
    }

    private AccessToken prepareAccessToken( OAuthTokenRequest request, long expireSeconds ) {
        accessTokenService.createByAuthorizationCode(
                new AccessToken(
                        dateExpired: dicideExpireDate( expireSeconds )
                ),
                authCodeService.readUnexpiredByCode( request.getCode(), AuthorizationCode_.scope )
        )
    }

    private static Date dicideExpireDate( long expireSeconds ) {
        if( expireSeconds <= 0 ) {
            return TimeBuilder.now().after( 30, TimeUnit.DAY ).buildDate()
        } else {
            return TimeBuilder.now().after( expireSeconds, TimeUnit.SECOND ).buildDate()
        }
    }

    private RefreshToken prepareRefreshToken( AccessToken accessToken ) {
        refreshTokenService.createByAccessToken(
            new RefreshToken(
                    dateExpired: TimeBuilder.now().after( 120, TimeUnit.MONTH ).buildDate()
            ),
            accessToken
        )
    }

    private static String buildResponseMessage( String accessToken, String refreshToken, long expireSeconds ) {
        return org.apache.oltu.oauth2.as.response.OAuthASResponse
                .tokenResponse( HttpServletResponse.SC_OK )
                .setAccessToken( accessToken )
                .setRefreshToken( refreshToken )
                .setTokenType( "Bearer" )
                .setExpiresIn( expireSeconds as String )
                .buildJSONMessage()
                .getBody()
    }

}
