package tw.edu.ncu.cc.oauth.server.service.oauth

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest
import org.apache.oltu.oauth2.common.error.OAuthError
import org.apache.oltu.oauth2.common.exception.OAuthProblemException
import org.apache.oltu.oauth2.common.exception.OAuthSystemException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.oauth.server.domain.AccessToken
import tw.edu.ncu.cc.oauth.server.helper.TimeBuilder
import tw.edu.ncu.cc.oauth.server.helper.data.TimeUnit
import tw.edu.ncu.cc.oauth.server.service.domain.AccessTokenService
import tw.edu.ncu.cc.oauth.server.service.domain.AuthorizationCodeService
import tw.edu.ncu.cc.oauth.server.service.domain.ClientService

import javax.servlet.http.HttpServletResponse

@Service( "AuthCodeExchangeService" )
class AuthorizationCodeExchangeService implements TokenExchangeService {

    @Autowired
    def ClientService clientService;

    @Autowired
    def AuthorizationCodeService authCodeService;

    @Autowired
    def AccessTokenService accessTokenService;

    private Logger logger = LoggerFactory.getLogger( this.getClass() );

    @Override
    String buildResonseMessage( OAuthTokenRequest request, long expireSeconds ) throws OAuthProblemException, OAuthSystemException {

        validateOauthRequest( request );

        String token = prepareAccessToken( request, expireSeconds );

        return buildResponseMessage( token, expireSeconds );
    }

    private void validateOauthRequest( OAuthTokenRequest request ) throws OAuthProblemException, OAuthSystemException {

        String clientID     = request.getClientId();
        String clientSecret = request.getClientSecret();
        String authCode     = request.getCode();

        logger.info(
                String.format(
                        "EXCHANGE CODE:%s CLIENT:%s",
                        authCode, clientID
                )
        );

        if ( ! clientService.isSerialIdSecretValid( clientID, clientSecret ) ) {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.INVALID_CLIENT, "INVALID CLIENT"
            );
        }

        if( ! authCodeService.isCodeUnexpiredWithClientId( authCode, clientID ) ) {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.INVALID_GRANT, "INVALID AUTH CODE"
            );
        }
    }

    private String prepareAccessToken( OAuthTokenRequest request, long expireSeconds ) {
        return accessTokenService.createByAuthorizationCode(
                new AccessToken(
                        dateExpired: dicideExpireDate( expireSeconds )
                ),
                authCodeService.readUnexpiredByRealCode( request.getCode(), [ 'client', 'scope', 'user' ] )
        ).getToken();
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
                .setExpiresIn( expireSeconds + "" )
                .buildJSONMessage()
                .getBody();
    }

}
