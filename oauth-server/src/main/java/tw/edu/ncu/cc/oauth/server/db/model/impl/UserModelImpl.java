package tw.edu.ncu.cc.oauth.server.db.model.impl;

import tw.edu.ncu.cc.oauth.server.db.data.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.db.data.UserEntity;
import tw.edu.ncu.cc.oauth.server.db.model.UserModel;
import tw.edu.ncu.cc.oauth.server.db.model.base.HibernateAccessTool;

import java.util.HashSet;

public class UserModelImpl extends HibernateAccessTool implements UserModel {

    @Override
    public void persistUsers( UserEntity... users ) {
        persistObjects( ( Object[] ) users );
    }

    @Override
    public UserEntity getUser( String name ) {
        UserEntity user = getObject(
                UserEntity.class,
                getSession()
                        .createQuery( "from UserEntity where name = :name" )
                        .setString( "name", name )
        );
        if( user != null && user.getTokens() == null ) {
            user.setTokens( new HashSet<AccessTokenEntity>() );
        }
        return user;
    }

}
