package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.entity.UserEntity;
import tw.edu.ncu.cc.oauth.server.repository.UserRepository;
import tw.edu.ncu.cc.oauth.server.service.UserService;

import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository( UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public UserEntity readUser( String name ) {
        return userRepository.readUserByName( name );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public UserEntity readUser( int id ) {
        return userRepository.readUserByID( id );
    }

    @Override
    @Transactional
    public UserEntity createUser( String name ) {
        UserEntity user = new UserEntity();
        user.setName( name );
        return userRepository.createUser( user );
    }

    @Override
    @Transactional
    public UserEntity createUserIfNotExist( String name ) {
        try {
            return readUser( name );
        } catch ( NoResultException ignore ) {
            return createUser( name );
        }
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public Set< AccessTokenEntity > readUserTokens( String name ) {
        return new HashSet<>( readUser( name ).getTokens() );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public Set< ClientEntity > readUserClients( String name ) {
        return new HashSet<>( readUser( name ).getClients() );
    }

}
