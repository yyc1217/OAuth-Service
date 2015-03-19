package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification

class PermissionServiceImplTest extends SpringSpecification {

    @Autowired
    private PermissionService permissionService

    def "it can read all permissions"() {
        expect:
            permissionService.readAll().size() == 3
    }

    def "it can read permission by id"() {
        expect:
            permissionService.readById( 1 ).getName() == "ADMIN"
    }

    def "it can read permission by name"() {
        expect:
            permissionService.readByName( "ADMIN" ).id == 1
    }

}