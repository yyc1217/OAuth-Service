package tw.edu.ncu.cc.oauth.server.db.model.impl;

import tw.edu.ncu.cc.oauth.server.db.data.UserEntity;
import tw.edu.ncu.cc.oauth.server.db.model.UserModel;
import tw.edu.ncu.cc.oauth.server.db.model.base.HibernateAccessTool;

public class UserModelImpl extends HibernateAccessTool implements UserModel {

    @Override
    public void persistUsers( UserEntity... users ) {
        persistObjects( ( Object[] ) users );
    }

    @Override
    public UserEntity getUser( String name ) {
        return getObject(
                UserEntity.class,
                getSession()
                        .createQuery( "from UserEntity where name = :name" )
                        .setString( "name", name )
        );
    }

}
