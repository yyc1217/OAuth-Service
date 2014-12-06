package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;

public interface AccessTokenRepository {
    public AccessTokenEntity revokeAccessToken( AccessTokenEntity accessToken );
    public AccessTokenEntity createAccessToken( AccessTokenEntity accessToken );
    public AccessTokenEntity readAccessToken( String token ) ;
    public AccessTokenEntity readAccessToken( int id );
}
