package tw.edu.ncu.cc.oauth.data;

import java.util.Set;

public class PermissionUtil {

    public static final Permission[] index = Permission.values();
    public static final int length = index.length;

    public static boolean isAllExist( Set<String> permissions ) {
        try{
            for( String permission : permissions ) {
                Permission.valueOf( permission );
            }
        } catch ( IllegalArgumentException | NullPointerException ignore ) {
            return false;
        }
        return true;
    }

    public static Permission[] valueOf( Set<String> permissionSet ) {
        Permission[] permissions = new Permission[ permissionSet.size() ];
        int i = 0;
        for( String permission : permissionSet ) {
            permissions[ i++ ] = Permission.valueOf( permission );
        }
        return permissions;
    }

}
