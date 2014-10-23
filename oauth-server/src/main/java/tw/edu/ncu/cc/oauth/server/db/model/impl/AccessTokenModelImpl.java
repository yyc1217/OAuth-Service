package tw.edu.ncu.cc.oauth.server.db.model.impl;

import tw.edu.ncu.cc.oauth.server.db.data.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.db.model.AccessTokenModel;
import tw.edu.ncu.cc.oauth.server.db.model.base.HibernateAccessTool;


public class AccessTokenModelImpl extends HibernateAccessTool implements AccessTokenModel {

    @Override
    public void persistAccessToken( AccessTokenEntity... accessToken ) {
        persistObjects( ( Object[] ) accessToken );
    }

    @Override
    public AccessTokenEntity getAccessToken( String token ) {
        return getObject(
                AccessTokenEntity.class,
                getSession()
                        .createQuery( "from AccessTokenEntity where token = :token" )
                        .setString( "token", token )
        );
    }

}
