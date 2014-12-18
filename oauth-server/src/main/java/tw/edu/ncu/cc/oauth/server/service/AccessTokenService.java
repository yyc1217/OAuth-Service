package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;

import java.util.Date;
import java.util.Set;


public interface AccessTokenService {

    public AccessTokenEntity createAccessToken( String clientID, String userID, Set< String > scope, Date expireDate );
    public AccessTokenEntity createAccessTokenByCode( String authCode, Date expireDate );
    public AccessTokenEntity readAccessTokenByToken ( String token );
    public AccessTokenEntity readAccessTokenByID  ( String id );
    public AccessTokenEntity revokeAccessTokenByID( String id );
    public Set< String > readTokenScopeByToken( String token );

}
