package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.helper.SecretCodec;
import tw.edu.ncu.cc.oauth.server.helper.StringGenerator;
import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret;
import tw.edu.ncu.cc.oauth.server.repository.AccessTokenRepository;
import tw.edu.ncu.cc.oauth.server.service.*;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.Set;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private UserService userService;
    private ClientService clientService;
    private PasswordEncoder passwordEncoder;
    private AuthCodeService authCodeService;
    private ScopeService scopeService;
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    public void setClientService( ClientService clientService ) {
        this.clientService = clientService;
    }

    @Autowired
    public void setAuthCodeService( AuthCodeService authCodeService ) {
        this.authCodeService = authCodeService;
    }

    @Autowired
    public void setScopeService( ScopeService scopeService ) {
        this.scopeService = scopeService;
    }

    @Autowired
    public void setUserService( UserService userService ) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder( PasswordEncoder passwordEncoder ) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAccessTokenRepository( AccessTokenRepository accessTokenRepository ) {
        this.accessTokenRepository = accessTokenRepository;
    }

    @Override
    @Transactional
    public AccessTokenEntity createAccessToken( String clientID, String userID, Set< String > scope, Date expireDate ) {
        AccessTokenEntity accessToken = new AccessTokenEntity();
        accessToken.setDateExpired( expireDate );
        accessToken.setUser( userService.readUser( userID ) );
        accessToken.setScope( scopeService.encode( scope ) );
        accessToken.setClient( clientService.readClientByID( clientID ) );
        return createAccessToken( accessToken );
    }

    @Override
    @Transactional
    public AccessTokenEntity createAccessTokenByCode( String code, Date expireDate ) {
        AuthCodeEntity authCode = authCodeService.readAuthCodeByCode( code );
        authCodeService.revokeAuthCodeByID( authCode.getId() + "" );
        AccessTokenEntity accessToken = new AccessTokenEntity();
        accessToken.setDateExpired( expireDate );
        accessToken.setScope( authCode.getScope() );
        accessToken.setUser( userService.readUser( authCode.getUser().getId() ) );
        accessToken.setClient( clientService.readClientByID( authCode.getClient().getId() + "" ) );
        return createAccessToken( accessToken );
    }

    private AccessTokenEntity createAccessToken( AccessTokenEntity accessToken ) {
        String token = StringGenerator.generateToken();
        accessToken.setToken( passwordEncoder.encode( token ) );
        AccessTokenEntity newAccessToken = accessTokenRepository.createAccessToken( accessToken );
        accessToken.setToken( SecretCodec.encode( new SerialSecret( newAccessToken.getId(), token ) ) );
        accessToken.setId( newAccessToken.getId() );
        return accessToken;
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AccessTokenEntity readAccessTokenByToken( String token ) {
        SerialSecret secret = SecretCodec.decode( token );
        AccessTokenEntity accessToken = accessTokenRepository.readUnexpiredAccessTokenByID( secret.getId() );
        if( passwordEncoder.matches( secret.getSecret(), accessToken.getToken() ) ) {
            return accessToken;
        } else {
            throw new NoResultException();
        }
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AccessTokenEntity readAccessTokenByID( String id ) {
        return accessTokenRepository.readUnexpiredAccessTokenByID( Integer.parseInt( id ) );
    }

    @Override
    @Transactional
    public AccessTokenEntity revokeAccessTokenByID( String id ) {
        return accessTokenRepository.revokeAccessToken(  readAccessTokenByID( id ) );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public Set< String > readTokenScopeByToken( String token ) {
        return scopeService.decode( readAccessTokenByToken( token ).getScope() );
    }

}
