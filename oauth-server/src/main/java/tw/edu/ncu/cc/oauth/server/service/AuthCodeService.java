package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;

public interface AuthCodeService {

    public AuthCodeEntity readAuthCode( int id );
    public AuthCodeEntity readAuthCode( String code );
    public AuthCodeEntity createAuthCode( AuthCodeEntity authCode );
    public AuthCodeEntity deleteAuthCode  ( AuthCodeEntity authCode );

}
