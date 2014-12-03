package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.data.v1.management.application.Application
import tw.edu.ncu.cc.oauth.server.service.ClientFactory
import tw.edu.ncu.cc.oauth.server.service.ClientService

class ClientFactoryImplTest extends SpringSpecification {

    @Autowired
    private ClientFactory clientBuilder

    @Autowired
    private ClientService clientService

    def "it can build a ClientEntity"() {
        when:
            def client = clientBuilder.createClient(
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

}
