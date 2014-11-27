package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;

import java.util.Set;

public interface AuthCodeBuilder {

    public AuthCodeEntity buildAuthCode( int clientID, String userID, Set< String > scope );

}
