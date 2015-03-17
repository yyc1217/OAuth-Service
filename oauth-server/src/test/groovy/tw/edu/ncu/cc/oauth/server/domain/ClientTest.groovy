package tw.edu.ncu.cc.oauth.server.domain

import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification


class ClientTest extends SpringSpecification {

    @Transactional
    def "it can map to exist data"() {
        given:
            def client = Client.get( 1 )
        expect:
            client.name == 'APP1'
            client.url  == 'http://example.com'
            client.callback == 'http://example.com'
            client.description == '1111'
            client.owner.name == 'ADMIN1'
    }

    @Transactional
    def "it's description and url can be null"() {
        given:
            def client = Client.get( 1 )
        when:
            client.url = null
            client.description = null
        then:
            client.save() != null
    }

}
