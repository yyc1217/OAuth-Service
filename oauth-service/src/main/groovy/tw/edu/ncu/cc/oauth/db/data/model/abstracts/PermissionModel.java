package tw.edu.ncu.cc.oauth.db.data.model.abstracts;

import tw.edu.ncu.cc.oauth.db.data.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionModel {
    public void persistPermissions( Permission...permissions );
    public List<Permission> getAllPermissions() ;
    public Set<Permission> convertToPermissions( Set<String> permissions );
    public boolean isPermissionsExist( Set<String> permissionSet );
}
