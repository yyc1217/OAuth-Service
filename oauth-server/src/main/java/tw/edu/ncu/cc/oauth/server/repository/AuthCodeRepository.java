package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;

public interface AuthCodeRepository {
    public AuthCodeEntity revokeAuthCode( AuthCodeEntity authCode );
    public AuthCodeEntity createAuthCode( AuthCodeEntity authCode );
    public AuthCodeEntity readAuthCode( String code );
    public AuthCodeEntity readAuthCode( int id );
}
