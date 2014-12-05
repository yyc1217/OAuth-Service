package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;


public interface AccessTokenService {

    public AccessTokenEntity readAccessToken( int id );
    public AccessTokenEntity readAccessToken( String token );
    public AccessTokenEntity createAccessToken( AccessTokenEntity accessToken );
    public AccessTokenEntity deleteAccessToken  ( AccessTokenEntity accessToken );

}
