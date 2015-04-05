package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification

class ClientServiceImplTest extends SpringSpecification {

    @Autowired
    private ClientService clientService

    def "it can create client"() {
        given:
            def client = new_client()
        when:
            def createdClient = clientService.create( client )
        then:
            createdClient.name       == client.name
            createdClient.owner.name == client.owner.name
    }

    @Transactional
    def "it can update exist client"() {
        given:
            def managedClient = clientService.readBySerialId( serialId( 1 ) )
        when:
            managedClient.name = 'newname'
        and:
            clientService.update( managedClient )
        then:
            clientService.readBySerialId( serialId( 1 ) ).name == 'newname'
    }

    def "it can delete client"() {
        given:
            def client = new_client()
        and:
            def createdClient = clientService.create( client )
        when:
            clientService.delete( createdClient )
        then:
            clientService.readBySerialId( serialId( createdClient.id ) ) == null
    }

    def "it can validate the client id and secret"() {
        given:
            def client = a_client()
        expect:
            clientService.isCredentialValid( serialId( client.id ), client.secret )
            ! clientService.isCredentialValid( serialId( 3 ), "SECR" )
    }

    def "it can refresh client secret"() {
        given:
            def client = new_client()
        and:
            def createdClient = clientService.create( client )
            def createdClientSerialId = serialId( createdClient.id )
        and:
            def originSecret = clientService.readBySerialId( createdClientSerialId ).secret
        when:
            clientService.refreshSecret( clientService.readBySerialId( createdClientSerialId ) )
        then:
            clientService.readBySerialId( createdClientSerialId ).secret != originSecret
    }

}
