package tw.edu.ncu.cc.oauth.server.db.model;

import tw.edu.ncu.cc.oauth.server.db.data.PermissionEntity;

import java.util.List;
import java.util.Set;

public interface PermissionModel {
    public void persistPermissions( PermissionEntity...permissions );
    public List<PermissionEntity> getAllPermissions() ;
    public Set<PermissionEntity> convertToPermissions( Set<String> permissions );
    public boolean isPermissionsExist( Set<String> permissionSet );
}
