package tw.edu.ncu.cc.oauth.server.repository.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository
import tw.edu.ncu.cc.oauth.server.repository.UserRepository


class ClientRepositoryImplTest extends SpringSpecification {

    @Autowired
    private ClientRepository clientRepository

    @Autowired
    private UserRepository userRepository

    @Transactional
    def "it can persist ClientEntity"() {
        when:
            def client = clientRepository.generateClient(
                    new ClientEntity(
                            name: "TEST APP",
                            owner: userRepository.getUser( 1 )
                    )
            )
        then:
            clientRepository.getClient( client.getId() ).getName() == "TEST APP"
    }

    @Transactional
    def "it can update ClientEntity"() {
        given:
            def client = clientRepository.getClient( 1 )
        when:
            client.setDescription( "NEW" )
        and:
            clientRepository.updateClient( client )
        then:
            clientRepository.getClient( client.getId() ).getDescription() == "NEW"
    }

    @Transactional
    def "it can delete ClientEntity"() {
        given:
            def client = clientRepository.generateClient(
                new ClientEntity(
                        name: "TEST APP",
                        owner: userRepository.getUser( 1 )
                )
            )
        when:
            clientRepository.deleteClient( client )
        then:
            clientRepository.getClient( client.getId() ) == null
    }

}
