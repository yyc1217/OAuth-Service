package tw.edu.ncu.cc.oauth.server.repository.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.entity.PermissionEntity
import tw.edu.ncu.cc.oauth.server.repository.PermissionRepository


class PermissionRepositoryImplTest extends SpringSpecification {

    @Autowired
    private PermissionRepository permissionRepository

    @Transactional
    def "it can generate PermissionEntity"() {
        when:
            permissionRepository.createPermission(
                new PermissionEntity(
                        name: "TEST"
                )
            )
        then:
            permissionRepository.readPermission( "TEST" ) != null
    }

    @Transactional
    def "it can delete PermissionEntity"() {
        given:
            def permission = permissionRepository.createPermission(
                    new PermissionEntity(
                            name: "TEST"
                    )
            )
        when:
            permissionRepository.deletePermission( permission )
        then:
            permissionRepository.readPermission( "TEST" ) == null
    }

    def "it can get all PermissionEntity"() {
        expect:
            permissionRepository.readAllPermissions().size() == 3
    }

}
