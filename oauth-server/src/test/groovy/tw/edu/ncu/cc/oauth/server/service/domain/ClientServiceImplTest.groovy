package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.client.ClientSearchDTO
import tw.edu.ncu.cc.oauth.server.concepts.client.ClientService

class ClientServiceImplTest extends SpringSpecification {

    @Autowired
    ClientService clientService

    @Transactional
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
            def managedClient = clientService.findUndeletedBySerialId( serialId( 1 ) )
        when:
            managedClient.name = 'newname'
        and:
            clientService.update( managedClient )
        then:
            clientService.findUndeletedBySerialId( serialId( 1 ) ).name == 'newname'
    }

    @Transactional
    def "it can delete client"() {
        given:
            def client = new_client()
        and:
            def createdClient = clientService.create( client )
        when:
            clientService.delete( createdClient )
        then:
            clientService.findUndeletedBySerialId( serialId( createdClient.id ) ) == null
    }

    def "it can validate the client id and secret"() {
        given:
            def client = a_client()
        expect:
            clientService.isCredentialValid( serialId( client.id ), client.encryptedSecret )
            ! clientService.isCredentialValid( serialId( 3 ), "SECR" )
    }

    @Transactional
    def "it can refresh client secret"() {
        given:
            def client = new_client()
        and:
            def createdClient = clientService.create( client )
            def createdClientSerialId = serialId( createdClient.id )
        and:
            def originEncryptedSecret = clientService.findUndeletedBySerialId( createdClientSerialId ).encryptedSecret
        when:
            clientService.refreshSecret( clientService.findUndeletedBySerialId( createdClientSerialId ) )
        then:
            clientService.findUndeletedBySerialId( createdClientSerialId ).encryptedSecret != originEncryptedSecret
    }

    @Transactional
    def "it can find clients by partial name"() {
        given:
            def client = new_client()
        and:
            clientService.create(client)

            def dto = new ClientSearchDTO()
            dto.name = client.name.substring(client.name.length() - 1)
        when:
            def results = clientService.findByDTO(dto)
        then:
            results.size() == 1
            results[0].name == client.name
    }

    @Transactional
    def "it can find clients by partial name, full id and full owner name"() {
        given:
            def client = new_client()
        and:
            def createdClient = clientService.create(client)
            clientService.create(client)

            def dto = new ClientSearchDTO()
            dto.name = createdClient.name.substring(createdClient.name.length() - 1)
            dto.id = createdClient.id
            dto.owner = createdClient.owner.name
        when:
            def results = clientService.findByDTO(dto)
        then:
            results.size() == 1
            results[0].name == createdClient.name
            results[0].id == createdClient.id
            results[0].owner == createdClient.owner
    }

    @Transactional
    def "it should find nothing because of client is deleted"() {
        given:
            def client = new_client()
            client.deleted = true
        and:
            def createdClient = clientService.create(client)
            clientService.create(client)

            def dto = new ClientSearchDTO()
            dto.name = createdClient.name.substring(createdClient.name.length() - 1)
            dto.id = createdClient.id
            dto.owner = createdClient.owner.name
        when:
            def results = clientService.findByDTO(dto)
        then:
            results.isEmpty()
    }
}
