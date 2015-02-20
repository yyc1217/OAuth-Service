package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;

import java.util.Date;
import java.util.Set;

public interface AuthCodeService {

    public AuthCodeEntity createAuthCode( String clientID, String userID, Set< String > scope, Date expireDate );
    public AuthCodeEntity readAuthCodeByCode( String code );
    public AuthCodeEntity readAuthCodeByID( String id );
    public AuthCodeEntity revokeAuthCodeByID( String id );

    public boolean isAuthCodeValid( String authCode, String clientID );
}
