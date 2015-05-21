package tw.edu.ncu.cc.oauth.server.web.oauth

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest
import org.apache.oltu.oauth2.common.error.OAuthError
import org.apache.oltu.oauth2.common.exception.OAuthProblemException
import org.apache.oltu.oauth2.common.exception.OAuthSystemException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tw.edu.ncu.cc.oauth.server.concepts.oauth.TokenExchangeService
import tw.edu.ncu.cc.oauth.server.helper.data.EditableRequest

import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

import static org.apache.oltu.oauth2.common.message.types.GrantType.AUTHORIZATION_CODE
import static org.apache.oltu.oauth2.common.message.types.GrantType.REFRESH_TOKEN
import static org.springframework.http.HttpHeaders.CACHE_CONTROL
import static org.springframework.http.HttpHeaders.PRAGMA

@RestController
public class TokenExchangeController {

    private HttpHeaders headers;

    @Value( '${custom.oauth.accessToken.expire-seconds}' )
    def long accessTokenExpireSeconds

    @Resource( name = "RefreshTokenExchangeService" )
    def TokenExchangeService refreshTokenService;

    @Resource( name = "AuthCodeExchangeService" )
    def TokenExchangeService authorizationCodeService;

    public TokenExchangeController() {
        headers = new HttpHeaders();
        headers.add( PRAGMA, "no-cache" );
        headers.add( CACHE_CONTROL, "no-store" );
    }

    @RequestMapping( value = "oauth/token", method = RequestMethod.POST )
    public ResponseEntity exchangeToken( HttpServletRequest httpRequest ) throws IOException {
        try {
            buildSuccessResponse( httpRequest )
        } catch ( OAuthSystemException ignore ) {
            buildServerError()
        } catch ( OAuthProblemException e ) {
            buildFailResponse( e )
        }
    }

    private def buildSuccessResponse( HttpServletRequest httpRequest ) throws OAuthProblemException, OAuthSystemException {
        OAuthTokenRequest tokenRequest = buildOAuthTokenRequest( httpRequest )
        return new ResponseEntity<>(
                decideAndBuildMessage( tokenRequest ), headers, HttpStatus.OK
        );
    }

    private static OAuthTokenRequest buildOAuthTokenRequest( HttpServletRequest httpRequest ) throws OAuthProblemException, OAuthSystemException {
        HttpServletRequest stubHttpRequest = new EditableRequest( httpRequest ).setParameter( "redirect_uri", "stub" ); //OLTU BUG
        return new OAuthTokenRequest( stubHttpRequest );
    }

    private String decideAndBuildMessage( OAuthTokenRequest tokenRequest ) throws OAuthProblemException, OAuthSystemException {
        String grantType = tokenRequest.getGrantType()

        if( grantType ==  AUTHORIZATION_CODE as String  && authorizationCodeService != null ) {

            authorizationCodeService.buildResonseMessage( tokenRequest, accessTokenExpireSeconds )

        } else if( grantType ==  REFRESH_TOKEN as String && refreshTokenService != null ) {

            refreshTokenService.buildResonseMessage( tokenRequest, accessTokenExpireSeconds )

        } else {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.UNSUPPORTED_GRANT_TYPE
            )
        }
    }

    private def buildServerError() {
        String body = buildErrorMessage( OAuthProblemException.error(
                OAuthError.CodeResponse.SERVER_ERROR )
        )
        return new ResponseEntity<>(
                body, headers, HttpStatus.BAD_REQUEST
        );
    }

    private def buildFailResponse( OAuthProblemException e ) {
        String body = buildErrorMessage( e )
        return new ResponseEntity<>(
                body, headers, HttpStatus.BAD_REQUEST
        );
    }

    private static String buildErrorMessage( OAuthProblemException e ) {
        return String.format(
                "{\"error\":\"%s\",\"error_description\":\"%s\"}",
                e.getError(),
                e.getDescription()
        );
    }

}
