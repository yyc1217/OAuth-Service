package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.service.UserService

import javax.persistence.NoResultException

class UserServiceImplTest extends SpringSpecification {

    @Autowired
    private UserService userService

    def "it can read UserEntity"() {
        when:
            userService.readUser( "USER_NOT_EXIST" )
        then:
            thrown( NoResultException )
    }

    def "it can create UserEntity"() {
        when:
            userService.createUser( "NEW_USER" )
            userService.readUser( "NEW_USER" )
        then:
            notThrown( NoResultException )
    }

    def "it can read user tokens 1"() {
        when:
            def tokens = userService.readUserTokens( "ADMIN1" )
        then:
            tokens.size() != 0
    }

    def "it can read user tokens 2"() {
        when:
            userService.readUserTokens( "NOT EXIST" )
        then:
            thrown( NoResultException )
    }

    def "it can read user clients"() {
        when:
            def clients = userService.readUserClients( "ADMIN1" )
        then:
            clients.size() != 0
    }

    def "it can create user if not exist"() {
        when:
            userService.createUserIfNotExist( "SAMEUSER" )
            userService.createUserIfNotExist( "SAMEUSER" )
        and:
            userService.readUser( "SAMEUSER" ) != null
        then:
            notThrown( NoResultException )
    }

}
