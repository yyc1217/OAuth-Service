package tw.edu.ncu.cc.oauth.server.repository.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity
import tw.edu.ncu.cc.oauth.server.repository.AccessTokenRepository
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository
import tw.edu.ncu.cc.oauth.server.repository.UserRepository

import javax.persistence.NoResultException

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
            accessTokenRepository.createAccessToken(
                new AccessTokenEntity(
                        token : "TEST01",
                        scope: "000",
                        user  : userRepository.readUser( 1 ),
                        client: clientRepository.readClient( 1 )
                )
            )
        then:
            accessTokenRepository.readUnexpiredAccessToken( "TEST01" ).user.id == 1
    }

    @Transactional
    def "it can revoke AccessTokenEntity"() {
        given:
            def token = accessTokenRepository.createAccessToken(
                new AccessTokenEntity (
                        token : "TEST02",
                        scope: "000",
                        user  : userRepository.readUser( 2 ),
                        client: clientRepository.readClient( 2 )
                )
            )
        when:
            accessTokenRepository.revokeAccessToken( token )
            accessTokenRepository.readUnexpiredAccessToken( "TEST02" )
        then:
             thrown( NoResultException )
    }

    def "it can read unexpired AccessTokenEntity by id"() {
        expect:
            accessTokenRepository.readUnexpiredAccessToken( 1 ).token == "TOKEN1"
    }

}
