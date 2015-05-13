package tw.edu.ncu.cc.oauth.server.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCodeRepository


class AuthorizationCodeRepositoryTest extends SpringSpecification {

    @Autowired
    private AuthorizationCodeRepository authorizationCodeRepository

    @Transactional
    def "it can map to exist data"() {
        when:
            def authorizationCode = authorizationCodeRepository.findOne( 1 )
        then:
            authorizationCode.encryptedCode == "CODE1"
            authorizationCode.client.name == "APP1"
            authorizationCode.user.name == "ADMIN1"
            authorizationCode.scope.size() == 2
    }

}
