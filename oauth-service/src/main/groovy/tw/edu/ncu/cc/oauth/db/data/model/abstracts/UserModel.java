package tw.edu.ncu.cc.oauth.db.data.model.abstracts;

import tw.edu.ncu.cc.oauth.db.data.User;

public interface UserModel {
    public void persistUsers( User... users );
    public User getUser( String name );
}
