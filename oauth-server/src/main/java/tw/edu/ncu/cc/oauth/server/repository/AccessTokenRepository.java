package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;

public interface AccessTokenRepository {
    public void deleteAccessToken( AccessTokenEntity accessToken );
    public AccessTokenEntity generateAccessToken( AccessTokenEntity accessToken );
    public AccessTokenEntity getAccessToken( String token ) ;
    public AccessTokenEntity getAccessToken( int id );
}
