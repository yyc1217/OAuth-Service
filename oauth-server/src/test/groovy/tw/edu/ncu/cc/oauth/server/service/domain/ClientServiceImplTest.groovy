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
            def client = clientService.readBySerialId( serialId( 1 ) )
        when:
            client.name = 'newname'
        and:
            clientService.update( client )
        then:
            clientService.readBySerialId( serialId( 1 ) ).name == 'newname'
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
            clientService.readBySerialId( serialId( client.id ) ) == null
    }

    def "it can validate the client id and secret"() {
        expect:
            clientService.isSerialIdSecretValid( serialId( 3 ), "SECRET" )
            ! clientService.isSerialIdSecretValid( serialId( 3 ), "SECR" )
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
            def clientSerialId = serialId( client.id )
        and:
            def originSecret = clientService.readBySerialId( clientSerialId ).secret
        when:
            clientService.refreshSecret( clientService.readBySerialId( clientSerialId ) )
        then:
            clientService.readBySerialId( clientSerialId ).secret != originSecret
    }

}
