package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.data.Permission;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.entity.UserEntity;

import java.util.Set;


public interface AccessTokenService {

    public AccessTokenEntity getAccessToken( String token ) ;

    public void deleteAccessToken ( AccessTokenEntity accessToken );

    public AccessTokenEntity generateAccessTokenFor( UserEntity user, ClientEntity client, Set<Permission> scope );

}
