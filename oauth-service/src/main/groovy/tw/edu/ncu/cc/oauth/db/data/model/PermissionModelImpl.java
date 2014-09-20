package tw.edu.ncu.cc.oauth.db.data.model;

import tw.edu.ncu.cc.oauth.db.data.Permission;
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.PermissionModel;
import tw.edu.ncu.cc.oauth.db.data.model.base.HibernateAccessTool;

import java.util.*;

public class PermissionModelImpl extends HibernateAccessTool implements PermissionModel {

    private static Map< String, Permission > permissionMap;

    @Override
    public void persistPermissions( Permission... permissions ) {
        persistObject( ( Object[] ) permissions );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public List<Permission> getAllPermissions() {
        return ( List<Permission> ) getSession()
                .createQuery( "from Permission " )
                .list();
    }

    @Override
    public Set<Permission> convertToPermissions( Set<String> permissions ) {
        Set<Permission> resultPermissions = new HashSet<>();
        if( ! isPermissionsExist( permissions ) ) {
            throw new IllegalArgumentException( "some permissions not exist" );
        }

        for( String permissionString : permissions ) {
            resultPermissions.add( permissionMap.get( permissionString ) );
        }

        return resultPermissions;
    }

    @Override
    public boolean isPermissionsExist( Set<String> permissionSet ) {
        if( permissionMap == null ) {
            permissionMap = createPermissionMap();
        }
        for( String permission : permissionSet ) {
            if( ! permissionMap.containsKey( permission ) ) {
                return false;
            }
        }
        return true;
    }

    private Map<String,Permission> createPermissionMap() {

        List<Permission> permissions = getAllPermissions();
        Map<String,Permission> map   = new Hashtable<>();

        for( Permission permission : permissions ) {
            map.put( permission.getName(), permission );
        }
        return map;
    }

}
