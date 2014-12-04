package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.entity.PermissionEntity
import tw.edu.ncu.cc.oauth.server.service.PermissionService


class PermissionServiceImplTest extends SpringSpecification {

    @Autowired
    private PermissionService permissionService

    @Transactional
    def "it can generate PermissionEntity"() {
        when:
            def permission = permissionService.generatePermission(
                new PermissionEntity(
                        name: "TESTNAME"
                )
            )
        then:
            permissionService.getPermission( permission.getName() ) != null
    }

    @Transactional
    def "it can delete PermissionEntity"() {
        given:
            def permission = permissionService.generatePermission(
                    new PermissionEntity(
                            name: "TESTNAME"
                    )
            )
        when:
            permissionService.deletePermission( permission )
        then:
            permissionService.getPermission( permission.getName() ) == null

    }

    def "it can get all PermissionEntity"() {
        expect:
            permissionService.getAllPermissions().size() == 3
    }

    def "it can get PermissionEntity by id"() {
        expect:
            permissionService.getPermission( 2 ).getName() == "READ"
    }

}
