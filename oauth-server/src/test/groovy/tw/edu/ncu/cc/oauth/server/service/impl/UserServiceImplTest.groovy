package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.entity.UserEntity
import tw.edu.ncu.cc.oauth.server.service.UserService


class UserServiceImplTest extends SpringSpecification {

    @Autowired
    private UserService userService

    def "it can persist UserEntity"() {
        when:
            userService.createUser(
                new UserEntity(
                        name: "NEW USER"
                )
            )
        then:
            userService.readUser( "NEW USER" ) != null
    }

}
