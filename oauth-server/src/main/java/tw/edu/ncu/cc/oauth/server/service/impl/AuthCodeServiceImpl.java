package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.helper.SecretCodec;
import tw.edu.ncu.cc.oauth.server.helper.StringGenerator;
import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret;
import tw.edu.ncu.cc.oauth.server.repository.AuthCodeRepository;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService;
import tw.edu.ncu.cc.oauth.server.service.ClientService;
import tw.edu.ncu.cc.oauth.server.service.ScopeService;
import tw.edu.ncu.cc.oauth.server.service.UserService;

import javax.persistence.NoResultException;
import java.util.Set;

@Service
public class AuthCodeServiceImpl implements AuthCodeService {

    private UserService userService;
    private ClientService clientService;
    private PasswordEncoder passwordEncoder;
    private ScopeService scopeService;
    private AuthCodeRepository authCodeRepository;

    @Autowired
    public void setUserService( UserService userService ) {
        this.userService = userService;
    }

    @Autowired
    public void setClientService( ClientService clientService ) {
        this.clientService = clientService;
    }

    @Autowired
    public void setScopeService( ScopeService scopeService ) {
        this.scopeService = scopeService;
    }

    @Autowired
    public void setPasswordEncoder( PasswordEncoder passwordEncoder ) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthCodeRepository( AuthCodeRepository authCodeRepository ) {
        this.authCodeRepository = authCodeRepository;
    }

    @Override
    @Transactional
    public AuthCodeEntity createAuthCode( String clientID, String userID, Set< String > scope ) {
        AuthCodeEntity authCode = new AuthCodeEntity();
        authCode.setUser( userService.readUser( userID ) );
        authCode.setClient( clientService.readClientByID( clientID ) );
        authCode.setScope( scopeService.encode( scope ) );
        String code = StringGenerator.generateToken();
        authCode.setCode( passwordEncoder.encode( code ) );
        AuthCodeEntity newAuthCode = authCodeRepository.createAuthCode( authCode );
        authCode.setCode( SecretCodec.encode( new SerialSecret( newAuthCode.getId(), code ) ) );
        authCode.setId( newAuthCode.getId() );
        return authCode;
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AuthCodeEntity readAuthCodeByID( String id ) {
        return authCodeRepository.readUnexpiredAuthCodeByID( Integer.parseInt( id ) );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AuthCodeEntity readAuthCodeByCode( String code ) {
        SerialSecret secret = SecretCodec.decode( code );
        AuthCodeEntity authCode = authCodeRepository.readUnexpiredAuthCodeByID( secret.getId() );
        if( passwordEncoder.matches( secret.getSecret(), authCode.getCode() ) ) {
            return authCode;
        } else {
            throw new NoResultException();
        }
    }

    @Override
    @Transactional
    public AuthCodeEntity revokeAuthCodeByID( String id ) {
        return authCodeRepository.revokeAuthCode( readAuthCodeByID( id ) );
    }

}
