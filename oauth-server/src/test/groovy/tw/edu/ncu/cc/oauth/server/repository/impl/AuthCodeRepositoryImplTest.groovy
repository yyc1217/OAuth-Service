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
            authCodeRepository.generateAuthCode(
                new AuthCodeEntity(
                        code : "TEST01",
                        permission: "000",
                        user  : userRepository.getUser( 1 ),
                        client: clientRepository.getClient( 1 )
                )
            )
        then:
            authCodeRepository.getAuthCode( "TEST01" ) != null
    }

    @Transactional
    def "it can delete AuthCodeEntity"() {
        given:
            def code = authCodeRepository.generateAuthCode(
                new AuthCodeEntity (
                        code : "TEST02",
                        permission: "000",
                        user  : userRepository.getUser( 2 ),
                        client: clientRepository.getClient( 2 )
                )
            )
        when:
            authCodeRepository.deleteAuthCode( code )
        then:
            authCodeRepository.getAuthCode( "TEST02" ) == null
    }

    def "it can get AuthCodeEntity by id"() {
        expect:
            authCodeRepository.getAuthCode( 1 ).getCode() == "CODE1"
    }

}
