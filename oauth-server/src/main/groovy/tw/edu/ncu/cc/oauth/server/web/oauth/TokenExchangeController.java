package tw.edu.ncu.cc.oauth.server.web.oauth;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ncu.cc.oauth.server.config.OauthConfig;
import tw.edu.ncu.cc.oauth.server.helper.data.EditableRequest;
import tw.edu.ncu.cc.oauth.server.service.TokenExchangeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.oltu.oauth2.common.message.types.GrantType.AUTHORIZATION_CODE;
import static org.apache.oltu.oauth2.common.message.types.GrantType.REFRESH_TOKEN;

@RestController
public class TokenExchangeController {

    private HttpHeaders headers;
    private OauthConfig oauthConfig;

    private TokenExchangeService refreshTokenService;
    private TokenExchangeService authorizationCodeService;

    public TokenExchangeController() {
        headers = new HttpHeaders();
        headers.add( HttpHeaders.PRAGMA, "no-cache" );
        headers.add( HttpHeaders.CACHE_CONTROL, "no-store" );
    }

    @Autowired
    public void setOauthConfig( OauthConfig oauthConfig ) {
        this.oauthConfig = oauthConfig;
    }

    public void setRefreshTokenService( TokenExchangeService refreshTokenService ) {
        this.refreshTokenService = refreshTokenService;
    }

    @Resource( name = "AuthCodeExchangeService" )
    public void setAuthorizationCodeService( TokenExchangeService authorizationCodeService ) {
        this.authorizationCodeService = authorizationCodeService;
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

    private OAuthTokenRequest buildOAuthTokenRequest( HttpServletRequest httpRequest ) throws OAuthProblemException, OAuthSystemException {
        HttpServletRequest stubHttpRequest = new EditableRequest( httpRequest ).setParameter( "redirect_uri", "stub" ); //OLTU BUG
        return new OAuthTokenRequest( stubHttpRequest );
    }

    private String buildSuccessMessage( OAuthTokenRequest tokenRequest ) throws OAuthProblemException, OAuthSystemException {
        return OAuthASResponse
            .tokenResponse( HttpServletResponse.SC_OK )
            .setAccessToken(
                    decideTokenService( tokenRequest ).createToken( tokenRequest, oauthConfig.getAccessTokenExpireSeconds() )
            )
            .setTokenType( "Bearer" )
            .setExpiresIn( oauthConfig.getAccessTokenExpireSeconds() + "" )
            .buildJSONMessage()
            .getBody();
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

    private String buildErrorMessage( OAuthProblemException e ) throws IOException {
        return String.format(
                "{\"error\":\"%s\",\"error_description\":\"%s\"}",
                e.getError(),
                e.getDescription()
        );
    }

}
