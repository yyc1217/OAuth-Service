package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.repository.AuthCodeRepository;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService;

@Service
public class AuthCodeServiceImpl implements AuthCodeService {

    private AuthCodeRepository authCodeRepository;

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
    public void generateAuthCode( AuthCodeEntity authCode ) {
        AuthCodeEntity authCodeEntity = new AuthCodeEntity();
        authCodeEntity.setCode( authCode.getCode() );
        authCodeEntity.setClient( authCode.getClient() );
        authCodeEntity.setUser( authCode.getUser() );
        authCodeEntity.setPermission( authCode.getPermission() );
        authCodeRepository.persistAuthCode( authCodeEntity );
    }

}
