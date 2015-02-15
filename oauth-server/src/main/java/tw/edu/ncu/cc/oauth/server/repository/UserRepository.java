package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.UserEntity;

public interface UserRepository {
    public UserEntity createUser( UserEntity user );
    public UserEntity readUserByName( String name );
    public UserEntity readUserByID( int id );
}
