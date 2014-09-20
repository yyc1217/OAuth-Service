package tw.edu.ncu.cc.oauth.db.data.model;

import tw.edu.ncu.cc.oauth.db.data.AuthCode;
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.AuthCodeModel;
import tw.edu.ncu.cc.oauth.db.data.model.base.HibernateAccessTool;

import java.util.List;

public class AuthCodemModelImpl extends HibernateAccessTool implements AuthCodeModel{

    @Override
    public void persistAuthCode( AuthCode... authCodes ) {
        persistObject( ( Object[] ) authCodes );
    }

    @Override
    public AuthCode getAuthCode( String code ) {
        List codes = getSession()
                .createQuery( "from AuthCode where code = :code" )
                .setString( "code", code )
                .list();
        if( codes.size() != 1 ) {
            return null;
        }else {
            return ( AuthCode ) codes.get( 0 );
        }
    }
}
