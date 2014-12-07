package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;

public interface AuthCodeRepository {
    public AuthCodeEntity revokeAuthCode( AuthCodeEntity authCode );
    public AuthCodeEntity createAuthCode( AuthCodeEntity authCode );
    public AuthCodeEntity readUnexpiredAuthCode( String code );
    public AuthCodeEntity readUnexpiredAuthCode( int id );
}
