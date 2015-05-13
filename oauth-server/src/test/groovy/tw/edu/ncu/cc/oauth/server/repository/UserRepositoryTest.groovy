package tw.edu.ncu.cc.oauth.server.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.user.User
import tw.edu.ncu.cc.oauth.server.concepts.user.UserRepository

class UserRepositoryTest extends SpringSpecification {

    @Autowired
    private UserRepository userRepository

    @Transactional
    def "it can map to exist data"() {
        when:
            def user = userRepository.findOne( 1 )
        then:
            user.name == 'ADMIN1'
            user.clients.size() == 1
            user.accessTokens.size() == 1
            user.codes.size() == 1
    }

    @Transactional
    def "it can create user"() {
        given:
            def user = new User( name: "HELLO" )
        when:
            userRepository.save( user )
        then:
            userRepository.findByName( "HELLO" ) != null
    }

}
