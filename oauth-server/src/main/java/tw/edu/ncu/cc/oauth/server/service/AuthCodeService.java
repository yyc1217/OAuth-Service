package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;

import java.util.Set;

public interface AuthCodeService {

    public AuthCodeEntity createAuthCode( int clientID, String userID, Set< String > scope );
    public AuthCodeEntity readAuthCodeByCode( String code );
    public AuthCodeEntity readAuthCodeByID( String id );
    public AuthCodeEntity revokeAuthCodeByID( String id );

}
