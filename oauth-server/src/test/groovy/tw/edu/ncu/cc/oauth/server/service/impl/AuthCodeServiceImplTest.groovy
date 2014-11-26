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
            def code = authCodeService.generateAuthCode(
                new AuthCodeEntity(
                        client: clientService.getClient( 1 ),
                        user  : userService.getUser( 1 ),
                        permission: "000"
                )
            )
        then:
            authCodeService.getAuthCode( code.getCode() ).getUser().getId() == 1
    }

    @Transactional
    def "it can delete AuthCodeEntity"() {
        given:
            def code = authCodeService.generateAuthCode(
                new AuthCodeEntity(
                        client: clientService.getClient( 1 ),
                        user  : userService.getUser( 1 ),
                        permission: "000"
                )
            )
        when:
            authCodeService.deleteAuthCode(
                    authCodeService.getAuthCode( code.getCode() )
            );
        then:
            authCodeService.getAuthCode( code.getCode() ) == null
    }

}
