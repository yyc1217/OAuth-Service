package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.PermissionEntity;

import java.util.List;

public interface PermissionRepository {

    public void deletePermission( PermissionEntity permission );
    public PermissionEntity generatePermission( PermissionEntity permission );
    public PermissionEntity getPermission( String name );
    public List< PermissionEntity > getAllPermissions();
}
