package tw.edu.ncu.cc.oauth.server.security.impl;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.security.OauthTokenService;
import tw.edu.ncu.cc.oauth.server.service.AccessTokenService;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService;
import tw.edu.ncu.cc.oauth.server.service.ClientService;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class AuthorizationCodeService implements OauthTokenService {

    private int codeExpireSeconds = 10*60;
    private int tokenExpireSeconds = 8*60*60;
    private ClientService clientService;
    private AuthCodeService authCodeService;
    private AccessTokenService accessTokenService;
    private Logger logger = LoggerFactory.getLogger( this.getClass() );

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
    public void setAccessTokenService( AccessTokenService accessTokenService ) {
        this.accessTokenService = accessTokenService;
    }

    @Override
    public void validate( OAuthTokenRequest request ) throws OAuthProblemException, OAuthSystemException {

        String clientID    = request.getClientId();
        String clientSecret = request.getClientSecret();
        String authCode     = request.getCode();

        logger.info(
                String.format(
                        "EXCHANGE [clientID:%s] [clientSecret:%s] [authCode:%s]",
                        clientID, clientSecret, authCode
                )
        );

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

    private boolean isAuthCodeValid( String authCode, String clientID ) {
        try {
            AuthCodeEntity code = authCodeService.readAuthCodeByCode( authCode );
            if( ! code.getClient().getId().toString().equals( clientID ) ) {
                return false;
            } else  if( codeExpireSeconds <= 0 ) {
                return true;
            } else {
                return new Date().before( new Date(
                        code.getDateCreated().getTime() + codeExpireSeconds * 1000
                ));
            }
        } catch ( NoResultException ignore ) {
            return false;
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
        return accessTokenService
                .createAccessTokenByCode(
                        request.getCode(),
                        ( tokenExpireSeconds <= 0 ? null : new Date( System.currentTimeMillis() + tokenExpireSeconds * 1000 ) )
                ).getToken();
    }

}
