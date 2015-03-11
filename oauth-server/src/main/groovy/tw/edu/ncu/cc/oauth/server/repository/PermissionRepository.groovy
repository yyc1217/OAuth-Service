package tw.edu.ncu.cc.oauth.server.repository

import tw.edu.ncu.cc.oauth.server.domain.PermissionEntity

public interface PermissionRepository {

    public void deletePermission( PermissionEntity permission );
    public PermissionEntity createPermission( PermissionEntity permission );
    public PermissionEntity readPermissionByName( String name );
    public PermissionEntity readPermissionByID( int id );
    public List< PermissionEntity > readAllPermissions();
}
