package tw.edu.ncu.cc.oauth.server.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.permission.PermissionRepository


class PermissionRepositoryTest extends SpringSpecification {

    @Autowired
    private PermissionRepository permissionRepository

    @Transactional
    def "it can map to exist data"() {
        when:
            def permission = permissionRepository.findOne( 1 )
        then:
            permission.name == "ADMIN"
    }

}
