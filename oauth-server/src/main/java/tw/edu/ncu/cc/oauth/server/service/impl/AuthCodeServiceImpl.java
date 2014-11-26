package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.helper.TokenGenerator;
import tw.edu.ncu.cc.oauth.server.repository.AuthCodeRepository;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService;

@Service
public class AuthCodeServiceImpl implements AuthCodeService {

    private AuthCodeRepository authCodeRepository;
    private TokenGenerator tokenGenerator = new TokenGenerator();

    @Autowired
    public void setAuthCodeRepository( AuthCodeRepository authCodeRepository ) {
        this.authCodeRepository = authCodeRepository;
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AuthCodeEntity getAuthCode( String code ) {
        return authCodeRepository.getAuthCode( code );
    }

    @Override
    @Transactional
    public void deleteAuthCode( AuthCodeEntity authCode ) {
        authCodeRepository.deleteAuthCode( authCode );
    }

    @Override
    @Transactional
    public AuthCodeEntity generateAuthCode( AuthCodeEntity authCode ) {
        String code = generateUnusedCode();
        authCode.setCode( code );
        AuthCodeEntity authCodeEntity = authCodeRepository.generateAuthCode( authCode );
        authCode.setCode( code );
        authCode.setId( authCodeEntity.getId() );
        return authCode;
    }

    private String generateUnusedCode() {
        while( true ) {
            String code = tokenGenerator.generate();
            if( authCodeRepository.getAuthCode( code ) == null ) {
                return code;
            }
        }
    }

}
