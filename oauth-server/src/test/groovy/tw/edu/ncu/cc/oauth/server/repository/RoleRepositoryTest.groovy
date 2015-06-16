package tw.edu.ncu.cc.oauth.server.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.role.RoleRepository

class RoleRepositoryTest extends SpringSpecification {

    @Autowired
    RoleRepository roleRepository

    @Transactional
    def "it can find role by name"() {
        given:
            def role = get_role( 1 )
        when:
            def managedRole = roleRepository.findByName( role.name )
        then:
            managedRole.name == role.name
    }

}
