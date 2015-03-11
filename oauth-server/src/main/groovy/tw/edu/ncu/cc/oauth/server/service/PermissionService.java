package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.domain.PermissionEntity;

import java.util.List;

public interface PermissionService {

    public void deletePermission( PermissionEntity permission );
    public PermissionEntity createPermission( PermissionEntity permission );
    public PermissionEntity readPermission( int id );
    public PermissionEntity readPermission( String name );
    public List< PermissionEntity > readAllPermissions();

}
