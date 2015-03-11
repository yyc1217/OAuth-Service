package tw.edu.ncu.cc.oauth.server.service.impl;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.edu.ncu.cc.oauth.server.helper.TimeBuilder;
import tw.edu.ncu.cc.oauth.server.helper.data.TimeUnit;
import tw.edu.ncu.cc.oauth.server.service.AccessTokenService;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService;
import tw.edu.ncu.cc.oauth.server.service.ClientService;
import tw.edu.ncu.cc.oauth.server.service.TokenExchangeService;

@Service( "AuthCodeExchangeService" )
public class AuthCodeExchangeService implements TokenExchangeService {

    private ClientService clientService;
    private AuthCodeService authCodeService;
    private AccessTokenService accessTokenService;

    private Logger logger = LoggerFactory.getLogger( this.getClass() );

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
    public String createToken( OAuthTokenRequest request, long expireSeconds ) throws OAuthProblemException, OAuthSystemException {

        validateOauthRequest( request );

        return prepareAccessToken( request, expireSeconds );
    }

    private void validateOauthRequest( OAuthTokenRequest request ) throws OAuthProblemException, OAuthSystemException {

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

        if( ! authCodeService.isAuthCodeValid( authCode, clientID ) ) {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.INVALID_GRANT, "INVALID AUTH CODE"
            );
        }
    }

    private String prepareAccessToken( OAuthTokenRequest request, long expireSeconds ) {
        return accessTokenService
                .createAccessTokenByCode(
                        request.getCode(),
                        ( expireSeconds <= 0 ? null : TimeBuilder.now().after( expireSeconds, TimeUnit.SECOND ).buildDate() )
                ).getToken();
    }

}
