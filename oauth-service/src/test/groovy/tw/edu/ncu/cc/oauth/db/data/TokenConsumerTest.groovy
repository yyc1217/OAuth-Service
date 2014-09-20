package tw.edu.ncu.cc.oauth.db.data

import spock.lang.Specification


class TokenConsumerTest extends Specification {

    private TokenConsumer tokenConsumer

    def setup() {
        tokenConsumer = new TokenConsumer()
    }

    def "has property of id in Integer"() {
        when:
            tokenConsumer.setId( 10 )
        then:
            tokenConsumer.getId() == 10
    }

    def "has property of ip in String"() {
        when:
            tokenConsumer.setIp( "192.168.0.1" )
        then:
            tokenConsumer.getIp() == "192.168.0.1"
    }

}
