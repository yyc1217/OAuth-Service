package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;


public interface AccessTokenService {

    public AccessTokenEntity getAccessToken( int id );
    public AccessTokenEntity getAccessToken( String token );
    public AccessTokenEntity generateAccessToken( AccessTokenEntity accessToken );
    public AccessTokenEntity deleteAccessToken  ( AccessTokenEntity accessToken );

}
