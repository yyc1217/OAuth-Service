package tw.edu.ncu.cc.oauth.db.data

import spock.lang.Specification


class AuthCodeTest extends Specification {

    private AuthCode authCode

    def setup() {
        authCode = new AuthCode()
    }

    def "has property of id in Integer"() {
        when:
            authCode.setId( 10 )
        then:
            authCode.getId() == 10
    }

    def "has property of code in String"() {
        when:
            authCode.setCode( "ABC123" )
        then:
            authCode.getCode() == "ABC123"
    }

    def "has property of client in Client"() {
        given:
            def client = new Client()
        when:
            authCode.setClient( client )
        then:
            authCode.getClient() == client
    }

    def "has property of scope in Permission set"() {
        given:
            def scope = new HashSet<Permission>()
        when:
            authCode.setScope( scope )
        then:
            authCode.getScope() == scope
    }
}
