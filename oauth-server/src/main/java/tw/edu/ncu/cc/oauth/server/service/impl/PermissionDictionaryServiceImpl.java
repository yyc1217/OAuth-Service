package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.edu.ncu.cc.oauth.server.data.PermissionDictionary;
import tw.edu.ncu.cc.oauth.server.repository.PermissionRepository;
import tw.edu.ncu.cc.oauth.server.service.PermissionDictionaryService;

@Service
public class PermissionDictionaryServiceImpl implements PermissionDictionaryService {

    private PermissionRepository permissionRepository;

    @Autowired
    public void setPermissionRepository( PermissionRepository permissionRepository ) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public PermissionDictionary generateDictionary() {
        return new PermissionDictionary( permissionRepository.getAllPermissions() );
    }

}
