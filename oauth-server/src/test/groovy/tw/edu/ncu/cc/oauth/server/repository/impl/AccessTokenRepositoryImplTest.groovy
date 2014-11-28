package tw.edu.ncu.cc.oauth.server.repository.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity
import tw.edu.ncu.cc.oauth.server.repository.AccessTokenRepository
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository
import tw.edu.ncu.cc.oauth.server.repository.UserRepository

class AccessTokenRepositoryImplTest extends SpringSpecification {

    @Autowired
    private AccessTokenRepository accessTokenRepository

    @Autowired
    private UserRepository userRepository

    @Autowired
    private ClientRepository clientRepository

    @Transactional
    def "it can persist AccessTokenEntity"() {
        when:
            accessTokenRepository.generateAccessToken(
                new AccessTokenEntity(
                        token : "TEST01",
                        scope: "000",
                        user  : userRepository.getUser( 1 ),
                        client: clientRepository.getClient( 1 )
                )
            )
        then:
            accessTokenRepository.getAccessToken( "TEST01" ) != null
    }

    @Transactional
    def "it can delete AccessTokenEntity"() {
        given:
            def token = accessTokenRepository.generateAccessToken(
                new AccessTokenEntity (
                        token : "TEST02",
                        scope: "000",
                        user  : userRepository.getUser( 2 ),
                        client: clientRepository.getClient( 2 )
                )
            )
        when:
            accessTokenRepository.deleteAccessToken( token )
        then:
            accessTokenRepository.getAccessToken( "TEST02" ) == null
    }

    def "it can get AccessTokenEntity by id"() {
        expect:
            accessTokenRepository.getAccessToken( 1 ).getToken() == "TOKEN1"
    }

}
