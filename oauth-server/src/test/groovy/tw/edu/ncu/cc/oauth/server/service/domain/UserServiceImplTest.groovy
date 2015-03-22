package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification

class UserServiceImplTest extends SpringSpecification {

    @Autowired
    private UserService userService

    def "it can create UserEntity"() {
        when:
            userService.createWithName( "NEW_USER" )
        then:
            userService.readByName( "NEW_USER" ) != null
    }

    def "it can create user if not exist"() {
        when:
            userService.createWithNameIfNotExist( "SAMEUSER" )
            userService.createWithNameIfNotExist( "SAMEUSER" )
        then:
            userService.readByName( "SAMEUSER" ) != null
    }

    def "it can read user with it's relational attributes"() {
        when:
            def user = userService.readByName( "ADMIN1", [ 'clients', 'accessTokens', 'codes' ] )
        then:
            user.clients.find { it.name == 'APP1' } != null
            user.accessTokens.find { it.token == 'TOKEN1' } != null
            user.codes.find { it.code == 'CODE1' } != null
    }

}
