package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService
import tw.edu.ncu.cc.oauth.server.service.ClientService
import tw.edu.ncu.cc.oauth.server.service.UserService


class AuthCodeServiceImplTest extends SpringSpecification {

    @Autowired
    private AuthCodeService authCodeService

    @Autowired
    private ClientService clientService

    @Autowired
    private UserService userService

    @Transactional
    def "it can generate AuthCodeEntity"() {
        when:
            def code = authCodeService.createAuthCode(
                new AuthCodeEntity(
                        client: clientService.readClient( 1 ),
                        user  : userService.readUser( 1 ),
                        scope: "000"
                )
            )
        then:
            authCodeService.readAuthCode( code.getCode() ).getUser().getId() == 1
    }

    @Transactional
    def "it can delete AuthCodeEntity"() {
        given:
            def code = authCodeService.createAuthCode(
                new AuthCodeEntity(
                        client: clientService.readClient( 1 ),
                        user  : userService.readUser( 1 ),
                        scope: "000"
                )
            )
        when:
            authCodeService.deleteAuthCode( authCodeService.readAuthCode( code.getId() ) );
        then:
            authCodeService.readAuthCode( code.getCode() ) == null
    }

    def "it can get AuthCodeEntity by id"() {
        expect:
            authCodeService.readAuthCode( 1 ).getCode() == "CODE1"
    }

}
