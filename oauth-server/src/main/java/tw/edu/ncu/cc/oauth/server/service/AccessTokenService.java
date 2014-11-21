package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.data.Permission;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;

import java.util.Set;

public interface AccessTokenService {
    public void persistAccessToken( AccessTokenEntity accessToken );
    public void deleteAccessToken ( AccessTokenEntity accessToken );
    public AccessTokenEntity getAccessToken( String token ) ;
    public Set<Permission>   getAccessTokenPermissions( String token );
}
