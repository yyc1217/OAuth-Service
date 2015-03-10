package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.domain.AccessTokenEntity;

public interface AccessTokenRepository {
    public AccessTokenEntity revokeAccessToken( AccessTokenEntity accessToken );
    public AccessTokenEntity createAccessToken( AccessTokenEntity accessToken );
    public AccessTokenEntity readUnexpiredAccessTokenByToken( String token );
    public AccessTokenEntity readUnexpiredAccessTokenByID( int id );
}
