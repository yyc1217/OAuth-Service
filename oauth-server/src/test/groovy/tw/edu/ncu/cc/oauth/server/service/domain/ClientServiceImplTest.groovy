package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.User

class ClientServiceImplTest extends SpringSpecification {

    @Autowired
    private ClientService clientService

    def "it can create client"() {
        when:
            def client = clientService.create(
                    new Client(
                            name: "HelloWorld",
                            description: "description",
                            callback: "abc://123",
                            owner: User.get( 1 ),
                            url: "http://example.com"
                    )
            )
        then:
            client.name == "HelloWorld"
            client.owner.name == "ADMIN1"
    }

    @Transactional
    def "it can update exist client"() {
        given:
            def client = clientService.readByID( 1 as String )
        when:
            client.name = 'newname'
        and:
            clientService.update( client )
        then:
            clientService.readByID( 1 as String ).name == 'newname'
    }

    def "it can delete client"() {
        given:
            def client = clientService.create(
                    new Client(
                            name: "HelloWorld",
                            description: "description",
                            callback: "abc://123",
                            owner: User.get( 1 ),
                            url: "http://example.com"
                    )
            )
        when:
            clientService.delete( client )
        then:
            clientService.readByID( client.id as String ) == null
    }

    def "it can validate the client id and secret"() {
        expect:
            clientService.isIdSecretValid( "3", "SECRET" )
            ! clientService.isIdSecretValid( "3", "SECR" )
    }

    def "it can refresh client secret"() {
        given:
            def client = clientService.create(
                    new Client(
                            name: "HelloWorld",
                            description: "description",
                            callback: "abc://123",
                            owner: User.get( 1 ),
                            url: "http://example.com"
                    )
            )
        and:
            def clientId = client.id as String
        and:
            def originSecret = clientService.readByID( clientId ).secret
        when:
            clientService.refreshSecret( clientService.readByID( clientId ) )
        then:
            clientService.readByID( clientId ).secret != originSecret
    }

}
