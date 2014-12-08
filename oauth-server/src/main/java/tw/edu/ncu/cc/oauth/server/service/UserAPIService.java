package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.entity.UserEntity;

import java.util.Set;

public interface UserAPIService {

    public UserEntity createUserIfNotExist( String name );
    public UserEntity createUser( String name );
    public UserEntity readUser  ( String name );
    public Set< ClientEntity > readUserClients( String name );
    public Set< AccessTokenEntity > readUserTokens( String name );

}
