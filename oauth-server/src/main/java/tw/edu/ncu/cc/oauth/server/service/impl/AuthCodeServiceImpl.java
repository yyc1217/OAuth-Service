package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.component.SecretCodec;
import tw.edu.ncu.cc.oauth.server.component.StringGenerator;
import tw.edu.ncu.cc.oauth.server.data.SerialSecret;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.repository.AuthCodeRepository;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService;

import java.util.Date;

@Service
public class AuthCodeServiceImpl implements AuthCodeService {

    private SecretCodec secretCodec;
    private PasswordEncoder passwordEncoder;
    private StringGenerator stringGenerator;
    private AuthCodeRepository authCodeRepository;

    @Autowired
    public void setSecretCodec( SecretCodec secretCodec ) {
        this.secretCodec = secretCodec;
    }

    @Autowired
    public void setPasswordEncoder( PasswordEncoder passwordEncoder ) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setStringGenerator( StringGenerator stringGenerator ) {
        this.stringGenerator = stringGenerator;
    }

    @Autowired
    public void setAuthCodeRepository( AuthCodeRepository authCodeRepository ) {
        this.authCodeRepository = authCodeRepository;
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AuthCodeEntity readAuthCode( int id ) {
        return authCodeRepository.readAuthCode( id );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AuthCodeEntity readAuthCode( String code ) {
        SerialSecret secret = secretCodec.decode( code );
        AuthCodeEntity authCode = authCodeRepository.readAuthCode( secret.getId() );
        if( authCode != null
                && passwordEncoder.matches( secret.getSecret(), authCode.getCode() )
                && ( authCode.getDateExpired() == null || authCode.getDateExpired().after( new Date() ) ) ) {
            return authCode;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public AuthCodeEntity revokeAuthCode( AuthCodeEntity authCode ) {
        return authCodeRepository.revokeAuthCode( authCode );
    }

    @Override
    @Transactional
    public AuthCodeEntity createAuthCode( AuthCodeEntity authCode ) {
        String code = stringGenerator.generateToken();
        authCode.setCode( passwordEncoder.encode( code ) );
        AuthCodeEntity newAuthCode = authCodeRepository.createAuthCode( authCode );
        authCode.setCode( secretCodec.encode( new SerialSecret( newAuthCode.getId(), code ) ) );
        authCode.setId( newAuthCode.getId() );
        return authCode;
    }

}
