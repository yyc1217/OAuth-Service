package tw.edu.ncu.cc.oauth.server.db.model.impl;

import tw.edu.ncu.cc.oauth.server.db.data.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.db.data.PermissionEntity;
import tw.edu.ncu.cc.oauth.server.db.model.AccessTokenModel;
import tw.edu.ncu.cc.oauth.server.db.model.base.HibernateAccessTool;

import java.util.HashSet;

public class AccessTokenModelImpl extends HibernateAccessTool implements AccessTokenModel {

    @Override
    public void persistAccessToken( AccessTokenEntity... accessToken ) {
        persistObjects( ( Object[] ) accessToken );
    }

    @Override
    public AccessTokenEntity getAccessToken( String token ) {
        AccessTokenEntity accessToken = getObject(
                AccessTokenEntity.class,
                getSession()
                        .createQuery( "from AccessTokenEntity where token = :token" )
                        .setString( "token", token )
        );
        if( accessToken != null && accessToken.getScope() == null ) {
            accessToken.setScope( new HashSet<PermissionEntity>() );
        }
        return accessToken;
    }

}
