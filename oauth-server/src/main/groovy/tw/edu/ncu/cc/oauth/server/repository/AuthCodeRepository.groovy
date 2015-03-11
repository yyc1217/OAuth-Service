package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.domain.AuthCodeEntity;

public interface AuthCodeRepository {
    public AuthCodeEntity revokeAuthCode( AuthCodeEntity authCode );
    public AuthCodeEntity createAuthCode( AuthCodeEntity authCode );
    public AuthCodeEntity readUnexpiredAuthCodeByCode( String code );
    public AuthCodeEntity readUnexpiredAuthCodeByID( int id );
}
