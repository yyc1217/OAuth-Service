package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;

import java.util.Set;


public interface AccessTokenBuilder {
    public AccessTokenEntity buildAccessToken( String authCode );
    public AccessTokenEntity buildAccessToken( int clientID, String userID, Set< String > scope );
}
