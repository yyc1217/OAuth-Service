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
    public AuthCodeEntity getAuthCode( String code ) {
        SerialSecret secret = secretCodec.decode( code );
        AuthCodeEntity authCode = authCodeRepository.getAuthCode( secret.getId() );
        if( authCode != null && passwordEncoder.matches( secret.getSecret(), authCode.getCode() ) ) {
            return authCode;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteAuthCode( AuthCodeEntity authCode ) {
        authCodeRepository.deleteAuthCode( authCode );
    }

    @Override
    @Transactional
    public AuthCodeEntity generateAuthCode( AuthCodeEntity authCode ) {
        String code = stringGenerator.generateToken();
        authCode.setCode( passwordEncoder.encode( code ) );
        AuthCodeEntity newAuthCode = authCodeRepository.generateAuthCode( authCode );
        authCode.setCode( secretCodec.encode( new SerialSecret( newAuthCode.getId(), code ) ) );
        authCode.setId( newAuthCode.getId() );
        return authCode;
    }

}
