package tw.edu.ncu.cc.oauth.server.db.model.impl;

import tw.edu.ncu.cc.oauth.server.db.data.PermissionEntity;
import tw.edu.ncu.cc.oauth.server.db.model.PermissionModel;
import tw.edu.ncu.cc.oauth.server.db.model.base.HibernateAccessTool;

import java.util.*;

public class PermissionModelImpl extends HibernateAccessTool implements PermissionModel {

    private static Map< String, PermissionEntity> permissionMap;

    @Override
    public void persistPermissions( PermissionEntity... permissions ) {
        persistObjects( ( Object[] ) permissions );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public List<PermissionEntity> getAllPermissions() {
        return getObjects( PermissionEntity.class, getSession().createQuery( "from PermissionEntity" ) );
    }

    @Override
    public Set<PermissionEntity> convertToPermissions( Set<String> permissions ) {

        if( ! isPermissionsExist( permissions ) ) {
            throw new IllegalArgumentException( "some permissions not exist:" );
        }

        Set<PermissionEntity> resultPermissions = new HashSet<>();

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

    private Map<String,PermissionEntity> createPermissionMap() {
        Map<String,PermissionEntity> map = new Hashtable<>();
        for( PermissionEntity permission : getAllPermissions() ) {
            map.put( permission.getName(), permission );
        }
        return map;
    }

}
