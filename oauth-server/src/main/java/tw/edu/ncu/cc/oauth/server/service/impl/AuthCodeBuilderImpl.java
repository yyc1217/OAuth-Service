package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.service.*;

import java.util.Set;

@Service
public class AuthCodeBuilderImpl implements AuthCodeBuilder {

    private UserService userService;
    private ClientService clientService;
    private AuthCodeService authCodeService;
    private ScopeCodecService scopeCodecService;

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

    @Override
    @Transactional
    public AuthCodeEntity buildAuthCode( int clientID, String userID, Set< String > scope ) {
        AuthCodeEntity authCode = new AuthCodeEntity();
        authCode.setUser( userService.getUser( userID ) );
        authCode.setClient( clientService.getClient( clientID ) );
        authCode.setScope( scopeCodecService.encode( scope ) );
        return authCodeService.generateAuthCode( authCode );
    }

}
