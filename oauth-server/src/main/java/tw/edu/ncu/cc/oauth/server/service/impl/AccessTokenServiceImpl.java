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
import tw.edu.ncu.cc.oauth.server.repository.AccessTokenRepository;
import tw.edu.ncu.cc.oauth.server.service.AccessTokenService;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private SecretCodec secretCodec;
    private PasswordEncoder passwordEncoder;
    private StringGenerator stringGenerator;
    private AccessTokenRepository accessTokenRepository;

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
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AccessTokenEntity readAccessToken( int id ) {
        return accessTokenRepository.readUnexpiredAccessToken( id );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AccessTokenEntity readAccessToken( String token ) {
        SerialSecret secret = secretCodec.decode( token );
        AccessTokenEntity accessToken = accessTokenRepository.readUnexpiredAccessToken( secret.getId() );
        if( accessToken != null && passwordEncoder.matches( secret.getSecret(), accessToken.getToken() ) ) {
            return accessToken;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public AccessTokenEntity revokeAccessToken( AccessTokenEntity accessToken ) {
        return accessTokenRepository.revokeAccessToken( accessToken );
    }

    @Override
    @Transactional
    public AccessTokenEntity createAccessToken( AccessTokenEntity accessToken ) {
        String token = stringGenerator.generateToken();
        accessToken.setToken( passwordEncoder.encode( token ) );
        AccessTokenEntity newAccessToken = accessTokenRepository.createAccessToken( accessToken );
        accessToken.setToken( secretCodec.encode( new SerialSecret( newAccessToken.getId(), token ) ) );
        accessToken.setId( newAccessToken.getId() );
        return accessToken;
    }

}
