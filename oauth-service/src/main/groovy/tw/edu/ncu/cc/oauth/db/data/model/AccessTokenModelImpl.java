package tw.edu.ncu.cc.oauth.db.data.model;

import tw.edu.ncu.cc.oauth.db.data.AccessToken;
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.AccessTokenModel;
import tw.edu.ncu.cc.oauth.db.data.model.base.HibernateAccessTool;

import java.util.List;

public class AccessTokenModelImpl extends HibernateAccessTool implements AccessTokenModel {

    @Override
    public void persistAccessToken( AccessToken... accessToken ) {
        persistObjects( ( Object[] ) accessToken );
    }

    @Override
    public AccessToken getAccessToken( String token ) {
        List tokens =  getSession()
                .createQuery( "from AccessToken where token = :token" )
                .setString( "token", token )
                .list();
        if( tokens.size() != 1 ) {
            return null;
        }else {
            return ( AccessToken ) tokens.get( 0 );
        }
    }

}
