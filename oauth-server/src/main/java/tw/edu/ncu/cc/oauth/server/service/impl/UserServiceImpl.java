package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.UserEntity;
import tw.edu.ncu.cc.oauth.server.repository.UserRepository;
import tw.edu.ncu.cc.oauth.server.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository( UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserEntity createUser( UserEntity user ) {
        return userRepository.createUser( user );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public UserEntity readUser( String name ) {
        return userRepository.readUser( name );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public UserEntity readUser( int id ) {
        return userRepository.readUser( id );
    }

}
