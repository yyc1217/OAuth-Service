package tw.edu.ncu.cc.oauth.server.repository.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository
import tw.edu.ncu.cc.oauth.server.repository.UserRepository

import javax.persistence.NoResultException


class ClientRepositoryImplTest extends SpringSpecification {

    @Autowired
    private ClientRepository clientRepository

    @Autowired
    private UserRepository userRepository

    @Transactional
    def "it can create ClientEntity"() {
        when:
            def client = clientRepository.createClient(
                    new ClientEntity(
                            name: "TEST APP",
                            owner: userRepository.readUserByID( 1 )
                    )
            )
        then:
            clientRepository.readClientByID( client.getId() ).name == "TEST APP"
    }

    @Transactional
    def "it can update ClientEntity"() {
        given:
            def client = clientRepository.readClientByID( 1 )
        when:
            client.setDescription( "NEW" )
        then:
            clientRepository.updateClient( client ).description == "NEW"
    }

    @Transactional
    def "it can delete ClientEntity"() {
        given:
            def client = clientRepository.createClient(
                new ClientEntity(
                        name: "TEST APP",
                        owner: userRepository.readUserByID( 1 )
                )
            )
        when:
            clientRepository.deleteClient( client )
            clientRepository.readClientByID( client.getId() )
        then:
            thrown( NoResultException )
    }

    @Transactional
    def "it can revoke all tokens of the specified client"() {
        when:
            clientRepository.revokeClientTokens( clientRepository.readClientByID( 1 ) )
        then:
            clientRepository.readClientByID( 1 ).getTokens().size() == 0
    }

}
