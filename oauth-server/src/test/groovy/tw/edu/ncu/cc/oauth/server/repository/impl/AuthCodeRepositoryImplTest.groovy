package tw.edu.ncu.cc.oauth.server.repository.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity
import tw.edu.ncu.cc.oauth.server.repository.AuthCodeRepository
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository
import tw.edu.ncu.cc.oauth.server.repository.UserRepository

class AuthCodeRepositoryImplTest extends SpringSpecification {

    @Autowired
    private AuthCodeRepository authCodeRepository

    @Autowired
    private UserRepository userRepository

    @Autowired
    private ClientRepository clientRepository

    @Transactional
    def "it can persist AuthCodeEntity"() {
        when:
            authCodeRepository.createAuthCode(
                new AuthCodeEntity(
                        code : "TEST01",
                        scope: "000",
                        user  : userRepository.readUser( 1 ),
                        client: clientRepository.readClient( 1 )
                )
            )
        then:
            authCodeRepository.readAuthCode( "TEST01" ) != null
    }

    @Transactional
    def "it can revoke AuthCodeEntity"() {
        given:
            def code = authCodeRepository.createAuthCode(
                new AuthCodeEntity (
                        code : "TEST02",
                        scope: "000",
                        user  : userRepository.readUser( 2 ),
                        client: clientRepository.readClient( 2 )
                )
            )
        when:
            authCodeRepository.revokeAuthCode( code )
        then:
            authCodeRepository.readAuthCode( "TEST02" ).getDateExpired().before( timeNow() )
    }

    def "it can get AuthCodeEntity by id"() {
        expect:
            authCodeRepository.readAuthCode( 1 ).getCode() == "CODE1"
    }

}
