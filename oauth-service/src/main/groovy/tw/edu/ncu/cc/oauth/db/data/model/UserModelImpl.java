package tw.edu.ncu.cc.oauth.db.data.model;

import tw.edu.ncu.cc.oauth.db.data.AccessToken;
import tw.edu.ncu.cc.oauth.db.data.User;
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.UserModel;
import tw.edu.ncu.cc.oauth.db.data.model.base.HibernateAccessTool;

import java.util.HashSet;
import java.util.List;

public class UserModelImpl extends HibernateAccessTool implements UserModel {

    @Override
    public void persistUsers( User... users ) {
        persistObjects( ( Object[] ) users );
    }

    @Override
    public User getUser( String name ) {
        List codes = getSession()
                .createQuery( "from User where name = :name" )
                .setString( "name", name )
                .list();
        if( codes.size() != 1 ) {
            return null;
        }else {
            User user = ( User ) codes.get( 0 );
            if( user.getTokens() == null ) {
                user.setTokens( new HashSet<AccessToken>() );
            }
            return user;
        }
    }

}
