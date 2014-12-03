package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;

import java.util.Set;


public interface AccessTokenFactory {
    public AccessTokenEntity createAccessToken( String authCode );
    public AccessTokenEntity createAccessToken( int clientID, String userID, Set< String > scope );
}
