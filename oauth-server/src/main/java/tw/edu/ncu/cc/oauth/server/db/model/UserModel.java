package tw.edu.ncu.cc.oauth.server.db.model;

import tw.edu.ncu.cc.oauth.server.db.data.UserEntity;

public interface UserModel {
    public void persistUsers( UserEntity... users );
    public UserEntity getUser( String name );
}
