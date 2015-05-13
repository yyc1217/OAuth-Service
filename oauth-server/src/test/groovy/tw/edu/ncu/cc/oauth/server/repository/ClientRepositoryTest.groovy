package tw.edu.ncu.cc.oauth.server.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.client.ClientRepository


class ClientRepositoryTest extends SpringSpecification {

    @Autowired
    private ClientRepository clientRepository

    @Transactional
    def "it can map to exist data"() {
        when:
            def client = clientRepository.findOne( 1 )
        then:
            client.name == 'APP1'
            client.url  == 'http://example.com'
            client.callback == 'http://example.com'
            client.description == '1111'
            client.owner.name == 'ADMIN1'
            client.encryptedSecret == 'SECRET1'
    }

}
