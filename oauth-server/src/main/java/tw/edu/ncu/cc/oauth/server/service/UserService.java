package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.UserEntity;

public interface UserService {
    public UserEntity createUser( UserEntity user );
    public UserEntity getUser( String name );
    public UserEntity getUser( int id );
}
