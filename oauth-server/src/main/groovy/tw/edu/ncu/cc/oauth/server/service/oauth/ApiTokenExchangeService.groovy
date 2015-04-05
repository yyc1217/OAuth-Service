package tw.edu.ncu.cc.oauth.server.service.oauth

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest
import org.apache.oltu.oauth2.common.error.OAuthError
import org.apache.oltu.oauth2.common.exception.OAuthProblemException
import org.apache.oltu.oauth2.common.exception.OAuthSystemException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tw.edu.ncu.cc.oauth.server.domain.ApiToken
import tw.edu.ncu.cc.oauth.server.helper.TimeBuilder
import tw.edu.ncu.cc.oauth.server.helper.data.TimeUnit
import tw.edu.ncu.cc.oauth.server.service.common.LogService
import tw.edu.ncu.cc.oauth.server.service.domain.ApiTokenService
import tw.edu.ncu.cc.oauth.server.service.domain.ClientService

import javax.servlet.http.HttpServletResponse

@Service( "ApiTokenExchangeService" )
class ApiTokenExchangeService implements TokenExchangeService {

    @Autowired
    def LogService logService

    @Autowired
    def ClientService clientService

    @Autowired
    def ApiTokenService apiTokenService

    @Override
    @Transactional
    String buildResonseMessage( OAuthTokenRequest request, long expireSeconds ) throws OAuthProblemException, OAuthSystemException {

        validateOauthRequest( request )

        ApiToken apiToken = prepareApiToken( request, expireSeconds )

        return buildResponseMessage( apiToken.token, expireSeconds )
    }

    private void validateOauthRequest( OAuthTokenRequest request ) {

        String clientID     = request.getClientId()
        String clientSecret = request.getClientSecret()

        logService.info(
                "EXCHANGE APITOKEN",
                "CLIENT:" + clientID
        )

        if ( ! clientService.isCredentialValid( clientID, clientSecret ) ) {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.INVALID_CLIENT, "INVALID CLIENT"
            )
        }
    }

    private ApiToken prepareApiToken( OAuthTokenRequest request, long expireSeconds ) {
        apiTokenService.create(
                new ApiToken(
                        client: clientService.readBySerialId( request.getClientId() ),
                        dateExpired: dicideExpireDate( expireSeconds )
                )
        )
    }

    private static Date dicideExpireDate( long expireSeconds ) {
        if( expireSeconds <= 0 ) {
            return TimeBuilder.now().after( 30, TimeUnit.DAY ).buildDate()
        } else {
            return TimeBuilder.now().after( expireSeconds, TimeUnit.SECOND ).buildDate()
        }
    }

    private static String buildResponseMessage( String apiToken, long expireSeconds ) {
        return org.apache.oltu.oauth2.as.response.OAuthASResponse
                .tokenResponse( HttpServletResponse.SC_OK )
                .setAccessToken( apiToken )
                .setTokenType( "Bearer" )
                .setExpiresIn( expireSeconds as String )
                .buildJSONMessage()
                .getBody()
    }

}
