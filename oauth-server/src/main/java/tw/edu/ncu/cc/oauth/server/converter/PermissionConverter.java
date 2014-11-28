package tw.edu.ncu.cc.oauth.server.converter;

import org.springframework.core.convert.converter.Converter;
import tw.edu.ncu.cc.oauth.server.entity.base.ScopeEntity;

import java.util.Set;

public class PermissionConverter implements Converter< ScopeEntity, Set<String> > {

    @Override
    public Set< String > convert( ScopeEntity source ) {
        return null;
    }

//    public boolean hasPermissions( Permission... permissions ) {
//        for( Permission p : permissions ) {
//            if( permission.charAt( p.ordinal() ) == PermissionRule.NO ) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void setPermissions( Permission...permissions ) {
//        StringBuilder stringBuilder = new StringBuilder( permission );
//        for( Permission p : permissions ) {
//            stringBuilder.setCharAt( p.ordinal(), PermissionRule.YES );
//        }
//        permission = stringBuilder.toString();
//    }

}
