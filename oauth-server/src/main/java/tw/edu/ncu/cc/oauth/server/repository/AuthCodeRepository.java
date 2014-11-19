package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;

public interface AuthCodeRepository {
    public void persistAuthCode( AuthCodeEntity authCode );
    public AuthCodeEntity getAuthCode( String code );
    public void deleteAuthCode( AuthCodeEntity authCode );
}
