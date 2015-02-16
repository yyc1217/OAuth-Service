package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.PermissionEntity;
import tw.edu.ncu.cc.oauth.server.repository.PermissionRepository;
import tw.edu.ncu.cc.oauth.server.service.PermissionService;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private PermissionRepository permissionRepository;

    @Autowired
    public void setPermissionRepository( PermissionRepository permissionRepository ) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    @Transactional
    public void deletePermission( PermissionEntity permission ) {
        permissionRepository.deletePermission( permission );
    }

    @Override
    @Transactional
    public PermissionEntity createPermission( PermissionEntity permission ) {
        return permissionRepository.createPermission( permission );
    }

    @Override
    @Cacheable( value="apiService", key="'PermissionID:' + #id" )
    public PermissionEntity readPermission( int id ) {
        return permissionRepository.readPermissionByID( id );
    }

    @Override
    @Cacheable( value="apiService", key="'PermissionName:' + #name" )
    public PermissionEntity readPermission( String name ) {
        return permissionRepository.readPermissionByName( name );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public List< PermissionEntity > readAllPermissions() {
        return permissionRepository.readAllPermissions();
    }

}
