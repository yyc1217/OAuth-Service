package tw.edu.ncu.cc.oauth.server.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessTokenRepository


class AccessTokenRepositoryTest extends SpringSpecification {

    @Autowired
    private AccessTokenRepository accessTokenRepository

    @Transactional
    def "it can map to exist data"() {
        when:
            def accessToken = accessTokenRepository.findOne( 1 )
        then:
            accessToken.encryptedToken == "TOKEN1"
            accessToken.client.name == "APP1"
            accessToken.user.name == "ADMIN1"
            accessToken.scope.size() == 2
    }

}
