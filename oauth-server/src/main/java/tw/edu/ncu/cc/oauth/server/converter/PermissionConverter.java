package tw.edu.ncu.cc.oauth.server.converter;

import org.springframework.core.convert.converter.Converter;
import tw.edu.ncu.cc.oauth.data.Permission;
import tw.edu.ncu.cc.oauth.server.entity.base.PermissionEntity;

import java.util.Set;

public class PermissionConverter implements Converter< PermissionEntity, Set<Permission> > {

    @Override
    public Set< Permission > convert( PermissionEntity source ) {
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
