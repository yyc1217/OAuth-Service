package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.service.UserService

class UserServiceImplTest extends SpringSpecification {

    @Autowired
    private UserService userService

    def "it can read UserEntity"() {
        when:
            userService.readUser( "USER_NOT_EXIST" )
        then:
            thrown( EmptyResultDataAccessException )
    }

    def "it can create UserEntity"() {
        when:
            userService.createUser( "NEW_USER" )
            userService.readUser( "NEW_USER" )
        then:
            notThrown( EmptyResultDataAccessException )
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
            thrown( EmptyResultDataAccessException )
    }

    def "it can read user clients"() {
        when:
            def clients = userService.readUserClients( "ADMIN1" )
        then:
            clients.size() != 0
    }

    def "it can create user if not exist"() {
        given:
            userService.createUserIfNotExist( "SAMEUSER" )
            userService.createUserIfNotExist( "SAMEUSER" )
        when:
            userService.readUser( "SAMEUSER" )
        then:
            notThrown( EmptyResultDataAccessException )
    }

}
