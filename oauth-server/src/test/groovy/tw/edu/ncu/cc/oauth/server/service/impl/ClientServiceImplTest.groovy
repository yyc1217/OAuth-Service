package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.data.v1.management.application.Application
import tw.edu.ncu.cc.oauth.server.service.ClientService
import tw.edu.ncu.cc.oauth.server.service.UserService

import javax.persistence.NoResultException

class ClientServiceImplTest extends SpringSpecification {

    @Autowired
    private ClientService clientService

    @Autowired
    private UserService userService

    @Transactional
    def "it can revoke ClientEntity all Tokens"() {
        given:
            def client = clientService.readClient( "1" )
        when:
            clientService.revokeClientTokens( "1" )
        then:
            client.getTokens().size() == 0
    }

    @Transactional
    def "it can refresh ClientEntity secret"() {
        expect:
            clientService.refreshClientSecret( "1" ).getSecret() != "SECRET1"
    }

    def "it can validate the client id and secret"() {
        when:
            def client = clientService.createClient(
                    new Application(
                            name: "TESTSERVICE",
                            owner: "ADMIN1"
                    )
            )
        then:
            clientService.isClientValid( client.getId() + "", client.getSecret() )
        and:
            ! clientService.isClientValid( client.getId() + "", "secret" )
            ! clientService.isClientValid( "50", "123" )
    }

    def "it can validate the client id and secret 2"() {
        expect:
            clientService.isClientValid( "3", "SECRET" )
            ! clientService.isClientValid( "3", "SECR" )
    }

    def "it can create ClientEntity"() {
        when:
            def client = clientService.createClient(
                    new Application(
                            name: "HelloWorld",
                            description: "description",
                            callback: "abc://123",
                            owner: "ADMIN1",
                            url: "http://example.com"
                    )
            )
        then:
            client.getName() == "HelloWorld"
            client.getOwner().getName() == "ADMIN1"
    }

    def "it can delete ClientEntity"() {
        given:
            def client = clientService.createClient(
                    new Application(
                            name: "HelloWorld",
                            description: "description",
                            callback: "abc://123",
                            owner: "ADMIN1",
                            url: "http://example.com"
                    )
            )
        when:
            clientService.deleteClient( client.getId() as String )
            clientService.readClient( client.getId() as String )
        then:
            thrown( NoResultException )
    }

    def "it can update ClientEntity"() {
        given:
            def client = clientService.createClient(
                    new Application(
                            name: "OriginName",
                            description: "description",
                            callback: "abc://123",
                            owner: "ADMIN1",
                            url: "http://example.com"
                    )
            )
        when:
            def newClient = clientService.updateClient( client.getId() as String, new Application(
                    name: "OtherName",
                    description: "description",
                    callback: "abc://123",
                    owner: "ADMIN1",
                    url: "http://example.com"
            ) )
        then:
            newClient.getName() == "OtherName"
    }

    def "it can refresh client secret"() {
        given:
            def client = clientService.createClient(
                    new Application(
                            name: "HelloWorld",
                            description: "description",
                            callback: "abc://123",
                            owner: "ADMIN1",
                            url: "http://example.com"
                    )
            )
        and:
            def originSecret = clientService.readClient( client.getId() as String ).getSecret()
        when:
            clientService.refreshClientSecret( client.getId() as String )
        then:
            clientService.readClient( client.getId() as String ).getSecret() != originSecret
    }

}
