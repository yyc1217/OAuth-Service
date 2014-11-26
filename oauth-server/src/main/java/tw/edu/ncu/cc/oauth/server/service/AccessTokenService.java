package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;


public interface AccessTokenService {

    public AccessTokenEntity getAccessToken( String token ) ;
    public AccessTokenEntity generateAccessToken( AccessTokenEntity accessToken );
    public void deleteAccessToken  ( AccessTokenEntity accessToken );

}
