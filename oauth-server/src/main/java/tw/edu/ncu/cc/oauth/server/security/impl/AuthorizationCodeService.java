package tw.edu.ncu.cc.oauth.server.security.impl;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.security.OauthTokenService;
import tw.edu.ncu.cc.oauth.server.service.AccessTokenAPIService;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService;
import tw.edu.ncu.cc.oauth.server.service.ClientService;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class AuthorizationCodeService implements OauthTokenService {

    private int codeExpireSeconds = 10*60;
    private int tokenExpireSeconds = 8*60*60;
    private ClientService clientService;
    private AuthCodeService authCodeService;
    private AccessTokenAPIService accessTokenAPIService;

    public void setCodeExpireSeconds( int codeExpireSeconds ) {
        this.codeExpireSeconds = codeExpireSeconds;
    }

    public void setTokenExpireSeconds( int tokenExpireSeconds ) {
        this.tokenExpireSeconds = tokenExpireSeconds;
    }

    @Autowired
    public void setClientService( ClientService clientService ) {
        this.clientService = clientService;
    }

    @Autowired
    public void setAuthCodeService( AuthCodeService authCodeService ) {
        this.authCodeService = authCodeService;
    }

    @Autowired
    public void setAccessTokenAPIService( AccessTokenAPIService accessTokenAPIService ) {
        this.accessTokenAPIService = accessTokenAPIService;
    }

    @Override
    public void validate( OAuthTokenRequest request ) throws OAuthProblemException, OAuthSystemException {

        Integer clientID    = Integer.valueOf( request.getClientId() );
        String clientSecret = request.getClientSecret();
        String authCode     = request.getCode();

        if ( ! clientService.isClientValid( clientID, clientSecret ) ) {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.INVALID_CLIENT, "INVALID CLIENT"
            );
        }

        if( ! isAuthCodeValid( authCode, clientID ) ) {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.INVALID_GRANT, "INVALID AUTH CODE"
            );
        }
    }

    private boolean isAuthCodeValid( String authCode, Integer clientID ) {
        AuthCodeEntity code = authCodeService.readAuthCode( authCode );
        if( code == null || ! code.getClient().getId().equals( clientID ) ) {
            return false;
        } else  if( codeExpireSeconds <= 0 ) {
            return true;
        } else {
            return new Date().before( new Date(
                            code.getDateCreated().getTime() + codeExpireSeconds * 1000
            ));
        }
    }

    @Override
    public String createResponseString( OAuthTokenRequest request ) throws OAuthSystemException {

        OAuthResponse response = OAuthASResponse
                .tokenResponse( HttpServletResponse.SC_OK )
                .setAccessToken( prepareAccessToken( request ) )
                .buildJSONMessage();

        return response.getBody();
    }

    private String prepareAccessToken( OAuthTokenRequest request ) {
        return accessTokenAPIService
                .createAccessTokenByCode(
                        request.getCode(),
                        new Date( System.currentTimeMillis() + tokenExpireSeconds * 1000 )
                ).getToken();
    }

}