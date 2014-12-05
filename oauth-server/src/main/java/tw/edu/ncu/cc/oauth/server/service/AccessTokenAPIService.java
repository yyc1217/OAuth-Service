package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;

import java.util.Set;


public interface AccessTokenAPIService {

    public AccessTokenEntity createAccessToken( int clientID, String userID, Set< String > scope );
    public AccessTokenEntity createAccessTokenByCode( String authCode );
    public AccessTokenEntity readAccessTokenByToken ( String token );
    public AccessTokenEntity readAccessTokenByID  ( String id );
    public AccessTokenEntity deleteAccessTokenByID( String id );
    public Set< String > readTokenScopeByToken( String token );

}
