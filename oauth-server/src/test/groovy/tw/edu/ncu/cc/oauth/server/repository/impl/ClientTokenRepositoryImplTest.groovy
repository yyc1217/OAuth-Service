package tw.edu.ncu.cc.oauth.server.repository.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository
import tw.edu.ncu.cc.oauth.server.repository.ClientTokenRepository

class ClientTokenRepositoryImplTest extends SpringSpecification {

    @Autowired
    private ClientRepository clientRepository

    @Autowired
    private ClientTokenRepository clientTokenRepository

    @Transactional
    def "it can delete all permitted tokens of the specified client"() {
        when:
            clientTokenRepository.deleteAllAccessTokenOfClient( clientRepository.getClient( 1 ) )
        then:
            clientRepository.getClient( 1 ).getTokens().size() == 0
    }

}
