package tw.edu.ncu.cc.oauth.server.domain

import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification

class AuthorizationCodeTest extends SpringSpecification {

    @Transactional
    def "it can map to exist data"() {
        given:
            def code = AuthorizationCode.get( 1 )
        expect:
            code.code == 'CODE1'
            code.client.name == 'APP1'
            code.user.name == 'ADMIN1'
    }

    @Transactional
    def "it can be revoked"() {
        given:
            def code = AuthorizationCode.get( 1 )
        when:
            code.revoke()
            code.save()
        and:
            def result = AuthorizationCode.unexpired.get( 1 )
        then:
            result == null
    }

}
