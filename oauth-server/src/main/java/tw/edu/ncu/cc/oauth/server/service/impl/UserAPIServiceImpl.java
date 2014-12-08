package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.entity.UserEntity;
import tw.edu.ncu.cc.oauth.server.service.UserAPIService;
import tw.edu.ncu.cc.oauth.server.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserAPIServiceImpl implements UserAPIService {

    private UserService userService;

    @Autowired
    public void setUserService( UserService userService ) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserEntity createUser( String name ) {
        UserEntity user = new UserEntity();
        user.setName( name );
        return userService.createUser( user );
    }

    @Override
    @Transactional
    public UserEntity createUserIfNotExist( String name ) {
        UserEntity user = readUser( name );
        return user == null ? createUser( name ) : user;
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public UserEntity readUser( String name ) {
        return userService.readUser( name );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public Set< AccessTokenEntity > readUserTokens( String name ) {
        UserEntity user = readUser( name );
        return user == null ? null : new HashSet<>( user.getTokens() );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public Set< ClientEntity > readUserClients( String name ) {
        UserEntity user = readUser( name );
        return user == null ? null : new HashSet<>( user.getClients() );
    }

}
