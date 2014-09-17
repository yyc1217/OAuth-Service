package tw.edu.ncu.cc.security.data

import spock.lang.Specification


class AccessTokenTest extends Specification {

    private AccessToken accessToken

    def setup() {
        accessToken = new AccessToken()
    }

    def "has property of id in Integer"() {
        when:
            accessToken.setId( 10 )
        then:
            accessToken.getId() == 10
    }

    def "has property of token in String"() {
        when:
            accessToken.setToken( "ABC123" )
        then:
            accessToken.getToken() == "ABC123"
    }

    def "has property of client in Client"() {
        given:
            def client = new Client()
        when:
            accessToken.setClient( client )
        then:
            accessToken.getClient() == client
    }

    def "has property of scope in Permission set"() {
        given:
            def scope = new HashSet<Permission>()
        when:
            accessToken.setScope( scope )
        then:
            accessToken.getScope() == scope
    }

}
