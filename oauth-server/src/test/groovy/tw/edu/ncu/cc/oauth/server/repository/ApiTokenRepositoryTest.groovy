package tw.edu.ncu.cc.oauth.server.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.apiToken.ApiTokenRepository


class ApiTokenRepositoryTest extends SpringSpecification {

    @Autowired
    private ApiTokenRepository apiTokenRepository

    @Transactional
    def "it can map to exist data"() {
        when:
            def apiToken = apiTokenRepository.findOne( 1 )
        then:
            apiToken.client.name == "APP1"
            apiToken.encryptedToken == "TOKEN1"
    }

}
