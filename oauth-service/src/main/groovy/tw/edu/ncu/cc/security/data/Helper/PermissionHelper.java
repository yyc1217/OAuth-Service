package tw.edu.ncu.cc.security.data.Helper;

import org.hibernate.Session;
import tw.edu.ncu.cc.security.data.Permission;
import tw.edu.ncu.cc.security.oauth.db.HibernateUtil;

import java.util.*;

public class PermissionHelper {

    private static Map<String,Permission> permissions;

    public static Set<Permission> convertStringToPermissions( String scope ) {

        if( permissions == null ) {
            permissions = createPermissionMap();
        }

        Set<Permission> resultPermissions = new HashSet<>();
        String[] permissionStringArray = scope.split( "," );
        for( String permissionString : permissionStringArray ) {
            if( permissions.containsKey( permissionString ) ) {
                resultPermissions.add( permissions.get( permissionString ) );
            }else{
                throw new IllegalArgumentException( "permission not exist:" + permissionString );
            }
        }

        return resultPermissions;
    }

    public static boolean isAllPermissionExist( Set<String> permissionSet ) {
        if( permissions == null ) {
            permissions = createPermissionMap();
        }
        for( String permission : permissionSet ) {
            if( ! permissions.containsKey( permission ) ) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings( "unchecked" )
    private static Map<String,Permission> createPermissionMap() {
        Session session = HibernateUtil.currentSession();

        List<Permission> permissionList = ( List<Permission> ) session
                .createQuery( "from Permission " )
                .list();

        Map<String,Permission> map = new Hashtable<>();
        for( Permission permission : permissionList ) {
            map.put( permission.getName(), permission );
        }
        return map;
    }

}
