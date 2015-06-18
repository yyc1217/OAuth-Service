package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.role.RoleService


class RoleServiceImplTest extends SpringSpecification {

    @Autowired
    RoleService roleService

    @Transactional
    def "it can find role by name"() {
        given:
            def role = get_role( 1 )
        expect:
            roleService.findByName( role.name ) != null
    }

}
