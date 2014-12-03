package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.service.*;

import java.util.Set;

@Service
public class AccessTokenFactoryImpl implements AccessTokenFactory {

    private UserService userService;
    private ClientService clientService;
    private AuthCodeService authCodeService;
    private ScopeCodecService scopeCodecService;
    private AccessTokenService accessTokenService;

    @Autowired
    public void setUserService( UserService userService ) {
        this.userService = userService;
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
    public void setScopeCodecService( ScopeCodecService scopeCodecService ) {
        this.scopeCodecService = scopeCodecService;
    }

    @Autowired
    public void setAccessTokenService( AccessTokenService accessTokenService ) {
        this.accessTokenService = accessTokenService;
    }

    @Override
    @Transactional
    public AccessTokenEntity createAccessToken( int clientID, String userID, Set< String > scope ) {
        AccessTokenEntity accessToken = new AccessTokenEntity();
        accessToken.setUser( userService.getUser( userID ) );
        accessToken.setScope( scopeCodecService.encode( scope ) );
        accessToken.setClient( clientService.getClient( clientID ) );
        return accessTokenService.generateAccessToken( accessToken );
    }

    @Override
    @Transactional
    public AccessTokenEntity createAccessToken( String code ) {
        AuthCodeEntity authCode = authCodeService.getAuthCode( code );
        authCodeService.deleteAuthCode( authCode.getId() );
        AccessTokenEntity accessToken = new AccessTokenEntity();
        accessToken.setScope( authCode.getScope() );
        accessToken.setUser( userService.getUser( authCode.getUser().getId() ) );
        accessToken.setClient( clientService.getClient( authCode.getClient().getId() ) );
        return accessTokenService.generateAccessToken( accessToken );
    }

}
