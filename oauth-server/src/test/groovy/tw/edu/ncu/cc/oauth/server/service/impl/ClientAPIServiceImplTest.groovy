package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.data.v1.management.application.Application
import tw.edu.ncu.cc.oauth.server.service.ClientAPIService
import tw.edu.ncu.cc.oauth.server.service.ClientService

class ClientAPIServiceImplTest extends SpringSpecification {

    @Autowired
    private ClientAPIService clientAPIService

    @Autowired
    private ClientService clientService

    def "it can create ClientEntity"() {
        when:
            def client = clientAPIService.createClient(
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
            def client = clientAPIService.createClient(
                    new Application(
                            name: "HelloWorld",
                            description: "description",
                            callback: "abc://123",
                            owner: "ADMIN1",
                            url: "http://example.com"
                    )
            )
        when:
            clientAPIService.deleteClient( client.getId() as String )
        then:
            clientAPIService.readClient( client.getId() as String ) == null
    }

    def "it can update ClientEntity"() {
        given:
            def client = clientAPIService.createClient(
                    new Application(
                            name: "OriginName",
                            description: "description",
                            callback: "abc://123",
                            owner: "ADMIN1",
                            url: "http://example.com"
                    )
            )
        when:
            def newClient = clientAPIService.updateClient( client.getId() as String, new Application(
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
            def client = clientAPIService.createClient(
                    new Application(
                            name: "HelloWorld",
                            description: "description",
                            callback: "abc://123",
                            owner: "ADMIN1",
                            url: "http://example.com"
                    )
            )
        and:
            def originSecret = clientAPIService.readClient( client.getId() as String ).getSecret()
        when:
            clientAPIService.refreshClientSecret( client.getId() as String )
        then:
            clientAPIService.readClient( client.getId() as String ).getSecret() != originSecret
    }

}
