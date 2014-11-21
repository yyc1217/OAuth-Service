package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;

public interface AuthCodeService {
    public void persistAuthCode( AuthCodeEntity authCode );
    public void deleteAuthCode ( AuthCodeEntity authCode );
    public AuthCodeEntity getAuthCode( String code );
}
