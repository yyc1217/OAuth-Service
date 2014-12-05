package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.PermissionEntity;
import tw.edu.ncu.cc.oauth.server.repository.PermissionRepository;
import tw.edu.ncu.cc.oauth.server.service.PermissionDictionaryService;
import tw.edu.ncu.cc.oauth.server.service.PermissionService;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private PermissionRepository permissionRepository;
    private PermissionDictionaryService dictionaryService;

    @Autowired
    public void setPermissionRepository( PermissionRepository permissionRepository ) {
        this.permissionRepository = permissionRepository;
    }

    @Autowired
    public void setDictionaryService( PermissionDictionaryService dictionaryService ) {
        this.dictionaryService = dictionaryService;
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
    public PermissionEntity readPermission( int id ) {
        return dictionaryService.createDictionary().getPermission( id );
    }

    @Override
    public PermissionEntity readPermission( String name ) {
        return dictionaryService.createDictionary().getPermission( name );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public List< PermissionEntity > readAllPermissions() {
        return permissionRepository.readAllPermissions();
    }

}
