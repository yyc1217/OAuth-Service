package tw.edu.ncu.cc.oauth.server.repository.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.entity.UserEntity
import tw.edu.ncu.cc.oauth.server.repository.UserRepository


class UserRepositoryImplTest extends SpringSpecification {

    @Autowired
    private UserRepository userRepository

    @Transactional
    def "it can persist UserEntity"() {
        when:
            userRepository.persistUser(
                    new UserEntity(
                            name: "TEST"
                    )
            )
        then:
            userRepository.getUser( "TEST" ) != null
    }

    def "it can get UserEntity by id"() {
        userRepository.getUser( 1 ).getName() == "ADMIN1"
    }

}
