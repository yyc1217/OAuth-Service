package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.PermissionEntity;

import java.util.List;

public interface PermissionRepository {

    public void deletePermission( PermissionEntity permission );
    public PermissionEntity createPermission( PermissionEntity permission );
    public PermissionEntity readPermissionByName( String name );
    public PermissionEntity readPermissionByID( int id );
    public List< PermissionEntity > readAllPermissions();
}
