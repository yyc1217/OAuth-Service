package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;

public interface AuthCodeService {

    public AuthCodeEntity getAuthCode( String code );
    public void deleteAuthCode  ( AuthCodeEntity authCode );
    public void generateAuthCode( AuthCodeEntity authCode );

}
