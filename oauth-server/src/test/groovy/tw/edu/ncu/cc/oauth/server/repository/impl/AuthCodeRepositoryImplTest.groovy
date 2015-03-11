package tw.edu.ncu.cc.oauth.server.repository.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.domain.AuthCodeEntity
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
    def "it can create AuthCodeEntity"() {
        when:
            authCodeRepository.createAuthCode(
                new AuthCodeEntity(
                        code : "TEST01",
                        scope: "000",
                        user  : userRepository.readUserByID( 1 ),
                        client: clientRepository.readClientByID( 1 )
                )
            )
        then:
            authCodeRepository.readUnexpiredAuthCodeByCode( "TEST01" ).user.id == 1
    }

    @Transactional
    def "it can revoke AuthCodeEntity"() {
        given:
            def code = authCodeRepository.createAuthCode(
                new AuthCodeEntity (
                        code : "TEST02",
                        scope: "000",
                        user  : userRepository.readUserByID( 2 ),
                        client: clientRepository.readClientByID( 2 )
                )
            )
        when:
            authCodeRepository.revokeAuthCode( code )
            authCodeRepository.readUnexpiredAuthCodeByCode( "TEST02" )
        then:
            thrown( EmptyResultDataAccessException )
    }

    def "it can read AuthCodeEntity by id"() {
        expect:
            authCodeRepository.readUnexpiredAuthCodeByID( 1 ).code == "CODE1"
    }

}
