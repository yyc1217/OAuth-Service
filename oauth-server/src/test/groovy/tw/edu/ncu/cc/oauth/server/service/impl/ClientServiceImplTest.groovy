package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity
import tw.edu.ncu.cc.oauth.server.service.ClientService
import tw.edu.ncu.cc.oauth.server.service.UserService

class ClientServiceImplTest extends SpringSpecification {

    @Autowired
    private ClientService clientService

    @Autowired
    private UserService userService

    @Transactional
    def "it can generate ClientEntity"() {
        when:
            def client = clientService.generateClient(
                new ClientEntity(
                        name : "TESTSERVICE",
                        owner: userService.getUser( 1 )
                )
            )
        then:
            clientService.getClient( client.getId() ).getName() == "TESTSERVICE"
    }

    @Transactional
    def "it can update ClientEntity"() {
        given:
            def client = clientService.getClient( 1 )
        when:
            client.setDescription( "HelloWorld!" )
        and:
            clientService.updateClient( client )
        then:
            clientService.getClient( client.getId() ).getDescription() == "HelloWorld!"
    }

    @Transactional
    def "it can delete ClientEntity"() {
        given:
            def client = clientService.getClient( 1 )
        when:
            clientService.deleteClient( client )
        then:
            clientService.getClient( client.getId() ) == null
    }

    @Transactional
    def "it can revoke ClientEntity all Tokens"() {
        given:
            def client = clientService.getClient( 1 )
        when:
            clientService.revokeClientTokens( client )
        then:
            client.getTokens().size() == 0
    }

    @Transactional
    def "it can refresh ClientEntity secret"() {
        given:
            def client = clientService.getClient( 1 )
        when:
            clientService.refreshClientSecret( client, "NEWSECRET" )
        then:
            clientService.getClient( client.getId() ).getSecret() == "NEWSECRET"
    }

    @Transactional
    def "it can validate the client id and secret"() {
        when:
            def client = clientService.generateClient(
                    new ClientEntity(
                            name : "TESTSERVICE",
                            owner: userService.getUser( 1 )
                    )
            )
        then:
            clientService.isClientValid( client.getId(), client.getSecret() )
        and:
            ! clientService.isClientValid( client.getId(), "secret" )
            ! clientService.isClientValid( 50, "123" )
    }

    def "it can validate the client id and secret 2"() {
        expect:
            clientService.isClientValid( 3, "SECRET" )
            ! clientService.isClientValid( 3, "SECR" )
    }

}
