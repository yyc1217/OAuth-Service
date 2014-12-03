package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.PermissionEntity;

import java.util.List;

public interface PermissionService {

    public void deletePermission( PermissionEntity permission );
    public PermissionEntity generatePermission( PermissionEntity permission );
    public PermissionEntity getPermission( int id );
    public PermissionEntity getPermission( String name );
    public List< PermissionEntity > getAllPermissions();

}
