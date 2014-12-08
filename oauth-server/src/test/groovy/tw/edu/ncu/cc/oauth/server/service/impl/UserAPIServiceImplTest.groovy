package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.service.UserAPIService


class UserAPIServiceImplTest extends SpringSpecification {

    @Autowired
    private UserAPIService userAPIService

    def "it can read UserEntity"() {
        expect:
            userAPIService.readUser( "USER_NOT_EXIST" ) == null
    }

    def "it can create UserEntity"() {
        when:
            userAPIService.createUser( "NEW_USER" )
        then:
            userAPIService.readUser( "NEW_USER" ) != null
    }

    def "it can read user tokens 1"() {
        when:
            def tokens = userAPIService.readUserTokens( "ADMIN1" )
        then:
            tokens.size() != 0
    }

    def "it can read user tokens 2"() {
        expect:
            userAPIService.readUserTokens( "NOT EXIST" ) == null
    }

    def "it can read user clients"() {
        when:
            def clients = userAPIService.readUserClients( "ADMIN1" )
        then:
            clients.size() != 0
    }

    def "it can create user if not exist"() {
        when:
            userAPIService.createUserIfNotExist( "SAMEUSER" )
            userAPIService.createUserIfNotExist( "SAMEUSER" )
        then:
            userAPIService.readUser( "SAMEUSER" ) != null
    }

}
