package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.domain.PermissionEntity
import tw.edu.ncu.cc.oauth.server.service.PermissionService

class PermissionServiceImplTest extends SpringSpecification {

    @Autowired
    private PermissionService permissionService

    @Transactional
    def "it can generate PermissionEntity"() {
        when:
            def permission = permissionService.createPermission(
                new PermissionEntity(
                        name: "TESTNAME"
                )
            )
        then:
            permissionService.readPermission( permission.getName() ) != null
    }

    @Transactional
    def "it can delete PermissionEntity"() {
        given:
            def permission = permissionService.createPermission(
                    new PermissionEntity(
                            name: "TESTNAME"
                    )
            )
        when:
            permissionService.deletePermission( permission )
        and:
            permissionService.readPermission( permission.getName() )
        then:
            thrown( EmptyResultDataAccessException )
    }

    def "it can get all PermissionEntity"() {
        expect:
            permissionService.readAllPermissions().size() == 3
    }

    def "it can get PermissionEntity by id"() {
        expect:
            permissionService.readPermission( 2 ).getName() == "READ"
    }

}
