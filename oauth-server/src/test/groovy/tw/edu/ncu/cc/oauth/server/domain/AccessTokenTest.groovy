package tw.edu.ncu.cc.oauth.server.domain

import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification

class AccessTokenTest extends SpringSpecification {

    def "it can map to exist data"() {
        given:
            def token = AccessToken.include( [ 'token', 'client', 'user', 'scope' ] ).get( 1 )
        expect:
            token.token == 'TOKEN1'
            token.client.name == 'APP1'
            token.user.name == 'ADMIN1'
//            token.scope.size() == 2 TODO fix
    }

    @Transactional
    def "it can be revoked"() {
        given:
            def token = AccessToken.get( 1 )
        when:
            token.revoke()
            token.save()
        and:
            def result = AccessToken.unexpired.get( 1 )
        then:
            result == null
    }

}
