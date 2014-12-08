package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.service.*;

import java.util.Date;
import java.util.Set;

@Service
public class AccessTokenAPIServiceImpl implements AccessTokenAPIService {

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
    public AccessTokenEntity createAccessToken( int clientID, String userID, Set< String > scope, Date expireDate ) {
        AccessTokenEntity accessToken = new AccessTokenEntity();
        accessToken.setDateExpired( expireDate );
        accessToken.setUser( userService.readUser( userID ) );
        accessToken.setScope( scopeCodecService.encode( scope ) );
        accessToken.setClient( clientService.readClient( clientID ) );
        return accessTokenService.createAccessToken( accessToken );
    }

    @Override
    @Transactional
    public AccessTokenEntity createAccessTokenByCode( String code, Date expireDate ) {
        AuthCodeEntity authCode = authCodeService.readAuthCode( code );
        authCodeService.revokeAuthCode( authCode );
        AccessTokenEntity accessToken = new AccessTokenEntity();
        accessToken.setDateExpired( expireDate );
        accessToken.setScope( authCode.getScope() );
        accessToken.setUser( userService.readUser( authCode.getUser().getId() ) );
        accessToken.setClient( clientService.readClient( authCode.getClient().getId() ) );
        return accessTokenService.createAccessToken( accessToken );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AccessTokenEntity readAccessTokenByToken( String token ) {
        return accessTokenService.readAccessToken( token );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AccessTokenEntity readAccessTokenByID( String id ) {
        return accessTokenService.readAccessToken( Integer.parseInt( id ) );
    }

    @Override
    @Transactional
    public AccessTokenEntity revokeAccessTokenByID( String id ) {
        AccessTokenEntity accessToken = readAccessTokenByID( id );
        return accessToken == null ? null : accessTokenService.revokeAccessToken( accessToken );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public Set< String > readTokenScopeByToken( String token ) {
        AccessTokenEntity accessToken = readAccessTokenByToken( token );
        return accessToken == null ? null : scopeCodecService.decode( accessToken.getScope() );
    }

}
