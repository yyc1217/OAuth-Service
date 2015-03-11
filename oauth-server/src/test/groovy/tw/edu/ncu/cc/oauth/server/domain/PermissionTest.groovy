package tw.edu.ncu.cc.oauth.server.domain

import specification.SpringSpecification


class PermissionTest extends SpringSpecification {

    def "it can map to exist data"() {
        given:
            def permission = Permission.get( 1 )
        expect:
            permission.name == 'ADMIN'
    }

}
