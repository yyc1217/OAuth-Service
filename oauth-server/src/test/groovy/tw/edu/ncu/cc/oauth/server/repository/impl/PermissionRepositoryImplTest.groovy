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
            permissionRepository.generatePermission(
                new PermissionEntity(
                        name: "TEST"
                )
            )
        then:
            permissionRepository.getPermission( "TEST" ) != null
    }

    @Transactional
    def "it can delete PermissionEntity"() {
        given:
            def permission = permissionRepository.generatePermission(
                    new PermissionEntity(
                            name: "TEST"
                    )
            )
        when:
            permissionRepository.deletePermission( permission )
        then:
            permissionRepository.getPermission( "TEST" ) == null
    }

    def "it can get all PermissionEntity"() {
        expect:
            permissionRepository.getAllPermissions().size() == 3
    }

}
