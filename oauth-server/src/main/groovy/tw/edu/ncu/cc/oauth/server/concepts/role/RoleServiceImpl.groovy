package tw.edu.ncu.cc.oauth.server.concepts.role

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl implements RoleService {

    @Autowired
    def RoleRepository roleRepository

    @Override
    @Cacheable( value="apiService", key="'RoleName:' + #name" )
    Role findByName( String name ) {
        roleRepository.findByName( name )
    }

}
