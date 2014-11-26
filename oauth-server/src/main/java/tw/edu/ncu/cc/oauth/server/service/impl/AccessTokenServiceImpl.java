package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.helper.TokenGenerator;
import tw.edu.ncu.cc.oauth.server.repository.AccessTokenRepository;
import tw.edu.ncu.cc.oauth.server.service.AccessTokenService;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private AccessTokenRepository accessTokenRepository;
    private TokenGenerator tokenGenerator = new TokenGenerator();

    @Autowired
    public void setAccessTokenRepository( AccessTokenRepository accessTokenRepository ) {
        this.accessTokenRepository = accessTokenRepository;
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public AccessTokenEntity getAccessToken( String token ) {
        return accessTokenRepository.getAccessToken( token );
    }

    @Override
    @Transactional
    public void deleteAccessToken( AccessTokenEntity accessToken ) {
        accessTokenRepository.deleteAccessToken( accessToken );
    }

    @Override
    @Transactional
    public AccessTokenEntity generateAccessToken( AccessTokenEntity accessToken ) {
        String token = generateUnusedToken();
        accessToken.setToken( token );
        AccessTokenEntity accessTokenEntity = accessTokenRepository.generateAccessToken( accessToken );
        accessToken.setToken( token );
        accessToken.setId( accessTokenEntity.getId() );
        return accessToken;
    }

    private String generateUnusedToken() {
        while( true ) {
            String token = tokenGenerator.generate();
            if( accessTokenRepository.getAccessToken( token ) == null ) {
                return token;
            }
        }
    }

}
