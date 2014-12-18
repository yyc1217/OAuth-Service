package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.component.SecretCodec;
import tw.edu.ncu.cc.oauth.server.component.StringGenerator;
import tw.edu.ncu.cc.oauth.server.data.SerialSecret;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.repository.AccessTokenRepository;
import tw.edu.ncu.cc.oauth.server.service.*;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.Set;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private SecretCodec secretCodec;
    private UserService userService;
    private ClientService clientService;
    private PasswordEncoder passwordEncoder;
    private StringGenerator stringGenerator;
    private AuthCodeService authCodeService;
    private ScopeCodecService scopeCodecService;
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
    public void setScopeCodecService( ScopeCodecService scopeCodecService ) {
        this.scopeCodecService = scopeCodecService;
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
    public void setSecretCodec( SecretCodec secretCodec ) {
        this.secretCodec = secretCodec;
    }

    @Autowired
    public void setStringGenerator( StringGenerator stringGenerator ) {
        this.stringGenerator = stringGenerator;
    }

    @Autowired
    public void setAccessTokenRepository( AccessTokenRepository accessTokenRepository ) {
        this.accessTokenRepository = accessTokenRepository;
    }

    @Override
    @Transactional
    public AccessTokenEntity createAccessToken( int clientID, String userID, Set< String > scope, Date expireDate ) {
        AccessTokenEntity accessToken = new AccessTokenEntity();
        accessToken.setDateExpired( expireDate );
        accessToken.setUser( userService.readUser( userID ) );
        accessToken.setScope( scopeCodecService.encode( scope ) );
        accessToken.setClient( clientService.readClient( clientID ) );
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
        accessToken.setClient( clientService.readClient( authCode.getClient().getId() ) );
        return createAccessToken( accessToken );
    }

    private AccessTokenEntity createAccessToken( AccessTokenEntity accessToken ) {
        String token = stringGenerator.generateToken();
        accessToken.setToken( passwordEncoder.encode( token ) );
        AccessTokenEntity newAccessToken = accessTokenRepository.createAccessToken( accessToken );
        accessToken.setToken( secretCodec.encode( new SerialSecret( newAccessToken.getId(), token ) ) );
        accessToken.setId( newAccessToken.getId() );
        return accessToken;
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AccessTokenEntity readAccessTokenByToken( String token ) {
        SerialSecret secret = secretCodec.decode( token );
        AccessTokenEntity accessToken = accessTokenRepository.readUnexpiredAccessToken( secret.getId() );
        if( passwordEncoder.matches( secret.getSecret(), accessToken.getToken() ) ) {
            return accessToken;
        } else {
            throw new NoResultException();
        }
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AccessTokenEntity readAccessTokenByID( String id ) {
        return accessTokenRepository.readUnexpiredAccessToken( Integer.parseInt( id ) );
    }

    @Override
    @Transactional
    public AccessTokenEntity revokeAccessTokenByID( String id ) {
        return accessTokenRepository.revokeAccessToken(  readAccessTokenByID( id ) );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public Set< String > readTokenScopeByToken( String token ) {
        return scopeCodecService.decode( readAccessTokenByToken( token ).getScope() );
    }

}
