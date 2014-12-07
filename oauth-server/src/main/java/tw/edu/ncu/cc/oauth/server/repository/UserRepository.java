package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.UserEntity;

public interface UserRepository {
    public UserEntity createUser( UserEntity user );
    public UserEntity readUser( String name );
    public UserEntity readUser( int id );
}
