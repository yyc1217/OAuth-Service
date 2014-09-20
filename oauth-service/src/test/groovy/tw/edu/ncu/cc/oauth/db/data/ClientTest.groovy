package tw.edu.ncu.cc.oauth.db.data

import spock.lang.Specification


class ClientTest extends Specification {

    private Client client

    def setup() {
        client = new Client()
    }

    def "has property of id in Integer"() {
        when:
            client.setId( 10 )
        then:
            client.getId() == 10
    }

    def "has property of secret in String"() {
        when:
            client.setSecret( "ABC123" )
        then:
            client.getSecret() == "ABC123"
    }

    def "has property of name in String"() {
        when:
            client.setName( "Hello" )
        then:
            client.getName() == "Hello"
    }

    def "has property of description in String"() {
        when:
            client.setDescription( "APP DES" )
        then:
            client.getDescription() == "APP DES"
    }

    def "has property of url in String"() {
        when:
            client.setUrl( "http://gamer.com" )
        then:
            client.getUrl() == "http://gamer.com"
    }

    def "has property of callback in String"() {
        when:
            client.setCallback( "schema://address" )
        then:
            client.getCallback() ==  "schema://address"
    }
}
