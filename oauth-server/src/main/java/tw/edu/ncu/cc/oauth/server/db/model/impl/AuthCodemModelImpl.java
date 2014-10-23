package tw.edu.ncu.cc.oauth.server.db.model.impl;

import tw.edu.ncu.cc.oauth.server.db.data.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.db.model.AuthCodeModel;
import tw.edu.ncu.cc.oauth.server.db.model.base.HibernateAccessTool;


public class AuthCodemModelImpl extends HibernateAccessTool implements AuthCodeModel {

    @Override
    public void persistAuthCodes( AuthCodeEntity... authCodes ) {
        persistObjects( ( Object[] ) authCodes );
    }

    @Override
    public void deleteAuthCodes( AuthCodeEntity... authCodes ) {
        deleteObjects( ( Object[] ) authCodes );
    }

    @Override
    public AuthCodeEntity getAuthCode( String code ) {
        return getObject(
                AuthCodeEntity.class,
                getSession()
                        .createQuery( "from AuthCodeEntity where code = :code" )
                        .setString( "code", code )
        );
    }

}
