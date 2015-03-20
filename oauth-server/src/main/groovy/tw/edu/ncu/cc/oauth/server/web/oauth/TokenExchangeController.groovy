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
import tw.edu.ncu.cc.oauth.server.helper.data.EditableRequest
import tw.edu.ncu.cc.oauth.server.service.oauth.TokenExchangeService

import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

import static org.apache.oltu.oauth2.common.message.types.GrantType.AUTHORIZATION_CODE
import static org.apache.oltu.oauth2.common.message.types.GrantType.REFRESH_TOKEN
import static org.springframework.http.HttpHeaders.CACHE_CONTROL
import static org.springframework.http.HttpHeaders.PRAGMA;


@RestController
public class TokenExchangeController {

    private HttpHeaders headers;

    @Value( '${custom.oauth.accessToken.expire-seconds}' )
    def long accessTokenExpireSeconds

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
            return new ResponseEntity<>(
                    buildSuccessMessage(
                            buildOAuthTokenRequest( httpRequest )
                    ), headers, HttpStatus.OK
            );
        } catch ( OAuthSystemException ignore ) {
            return new ResponseEntity<>(
                    buildErrorMessage( OAuthProblemException.error(
                        OAuthError.CodeResponse.SERVER_ERROR )
                    ), headers, HttpStatus.BAD_REQUEST
            );
        } catch ( OAuthProblemException e ) {
            return new ResponseEntity<>(
                    buildErrorMessage( e )
                    , headers, HttpStatus.BAD_REQUEST
            );
        }
    }

    private static OAuthTokenRequest buildOAuthTokenRequest( HttpServletRequest httpRequest ) throws OAuthProblemException, OAuthSystemException {
        HttpServletRequest stubHttpRequest = new EditableRequest( httpRequest ).setParameter( "redirect_uri", "stub" ); //OLTU BUG
        return new OAuthTokenRequest( stubHttpRequest );
    }

    private String buildSuccessMessage( OAuthTokenRequest tokenRequest ) throws OAuthProblemException, OAuthSystemException {
        return decideTokenService( tokenRequest )
                .buildResonseMessage( tokenRequest, accessTokenExpireSeconds )
    }

    private TokenExchangeService decideTokenService( OAuthTokenRequest tokenRequest ) throws OAuthProblemException {
        String grantType = tokenRequest.getGrantType();
        if( grantType.equals(  AUTHORIZATION_CODE.toString() ) && authorizationCodeService != null ) {
            return authorizationCodeService;
        } else if( grantType.equals( REFRESH_TOKEN.toString() ) && refreshTokenService != null ) {
            return refreshTokenService;
        } else {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.UNSUPPORTED_GRANT_TYPE
            );
        }
    }

    private static String buildErrorMessage( OAuthProblemException e ) throws IOException {
        return String.format(
                "{\"error\":\"%s\",\"error_description\":\"%s\"}",
                e.getError(),
                e.getDescription()
        );
    }

}
