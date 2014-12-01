package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;

public interface AuthCodeRepository {
    public void deleteAuthCode( int id );
    public AuthCodeEntity generateAuthCode( AuthCodeEntity authCode );
    public AuthCodeEntity getAuthCode( String code );
    public AuthCodeEntity getAuthCode( int id );
}
