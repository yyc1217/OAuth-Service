package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.user.UserService

class UserServiceImplTest extends SpringSpecification {

    @Autowired
    private UserService userService

    @Transactional
    def "it can create UserEntity"() {
        when:
            userService.createByName( "NEW_USER" )
        then:
            userService.findByName( "NEW_USER" ) != null
    }

    @Transactional
    def "it can create user if not exist"() {
        when:
            userService.createByNameIfNotExist( "SAMEUSER" )
            userService.createByNameIfNotExist( "SAMEUSER" )
        then:
            userService.findByName( "SAMEUSER" ) != null
    }

}
