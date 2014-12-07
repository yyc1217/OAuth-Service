package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable( value = "permissionDictionary", key = "'dictionary'" )
    public PermissionDictionary createDictionary() {
        return new PermissionDictionary( permissionRepository.readAllPermissions() );
    }

}
