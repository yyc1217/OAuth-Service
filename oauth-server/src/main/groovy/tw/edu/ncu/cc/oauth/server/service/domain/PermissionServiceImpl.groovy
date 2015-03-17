package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tw.edu.ncu.cc.oauth.server.domain.Permission

@Service
@Transactional
class PermissionServiceImpl implements PermissionService {

    @Override
    @Cacheable( value="apiService", key="'PermissionID:' + #id" )
    Permission readById( long id ) {
        return Permission.findById( id )
    }

    @Override
    @Cacheable( value="apiService", key="'PermissionName:' + #name" )
    Permission readByName( String name ) {
        return Permission.findByName( name )
    }

    @Override
    List< Permission > readAll() {
        return Permission.findAll()
    }

}
