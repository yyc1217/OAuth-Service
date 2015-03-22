package tw.edu.ncu.cc.oauth.server.domain

import specification.SpringSpecification

class ClientTest extends SpringSpecification {

    def "it can map to exist data"() {
        given:
            def client = Client.include( [ 'owner' ] ).get( 1 )
        expect:
            client.name == 'APP1'
            client.url  == 'http://example.com'
            client.callback == 'http://example.com'
            client.description == '1111'
            client.owner.name == 'ADMIN1'
    }

}
