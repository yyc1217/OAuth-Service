package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.permission.PermissionService

class PermissionServiceImplTest extends SpringSpecification {

    @Autowired
    private PermissionService permissionService

    def "it can read all permissions"() {
        expect:
            permissionService.findAll().size() == 3
    }

    def "it can read permission by id"() {
        given:
            def permission = a_permission()
        expect:
            permissionService.findById( permission.id ).name == permission.name
    }

    def "it can read permission by name"() {
        given:
            def permission = a_permission()
        expect:
            permissionService.findByName( permission.name ).id == permission.id
    }

}
