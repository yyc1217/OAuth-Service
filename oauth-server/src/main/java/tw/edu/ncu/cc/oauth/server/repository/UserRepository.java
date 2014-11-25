package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.UserEntity;

public interface UserRepository {
    public UserEntity updateUser( UserEntity user );
    public UserEntity generateUser( UserEntity user );
    public UserEntity getUser( String name );
    public UserEntity getUser( int id );
}
