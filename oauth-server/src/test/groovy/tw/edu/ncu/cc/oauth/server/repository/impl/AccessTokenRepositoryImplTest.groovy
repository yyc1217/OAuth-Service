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
                        user  : userRepository.readUserByID( 1 ),
                        client: clientRepository.readClientByID( 1 )
                )
            )
        then:
            accessTokenRepository.readUnexpiredAccessTokenByToken( "TEST01" ).user.id == 1
    }

    @Transactional
    def "it can revoke AccessTokenEntity"() {
        given:
            def token = accessTokenRepository.createAccessToken(
                new AccessTokenEntity (
                        token : "TEST02",
                        scope: "000",
                        user  : userRepository.readUserByID( 2 ),
                        client: clientRepository.readClientByID( 2 )
                )
            )
        when:
            accessTokenRepository.revokeAccessToken( token )
            accessTokenRepository.readUnexpiredAccessTokenByToken( "TEST02" )
        then:
             thrown( NoResultException )
    }

    def "it can read unexpired AccessTokenEntity by id"() {
        expect:
            accessTokenRepository.readUnexpiredAccessTokenByID( 1 ).token == "TOKEN1"
    }

}
