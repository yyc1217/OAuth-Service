package tw.edu.ncu.cc.oauth.db.data

import spock.lang.Specification


class UserTest extends Specification {

    private User user

    def setup() {
        user = new User()
    }

    def "has property of id in Integer"() {
        when:
            user.setId( 10 )
        then:
            user.getId() == 10
    }

    def "has property of account in String"() {
        when:
            user.setAccount( "myaccount" )
        then:
            user.getAccount() == "myaccount"
    }

    def "has property of tokens in AccessToken set"() {
        given:
            def tokens = new HashSet<AccessToken>()
        when:
            user.setTokens( tokens )
        then:
            user.getTokens() == tokens
    }

}
