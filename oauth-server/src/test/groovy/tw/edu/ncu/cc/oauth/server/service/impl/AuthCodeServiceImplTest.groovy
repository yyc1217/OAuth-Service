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
    def "it can generate AuthCodeEntity with encoded code"() {
        when:
            authCodeService.generateAuthCode(
                new AuthCodeEntity(
                        code  : "TEST",
                        client: clientService.getClient( 1 ),
                        user  : userService.getUser( 1 ),
                        permission: "000"
                )
            )
        then:
            authCodeService.getAuthCode( "TEST" ).getUser().getId() == 1
    }

    @Transactional
    def "it can delete AuthCodeEntity"() {
        given:
            authCodeService.generateAuthCode(
                new AuthCodeEntity(
                        code  : "TEST",
                        client: clientService.getClient( 1 ),
                        user  : userService.getUser( 1 ),
                        permission: "000"
                )
            )
        when:
            authCodeService.deleteAuthCode(
                    authCodeService.getAuthCode( "TEST" )
            );
        then:
            authCodeService.getAuthCode( "TEST" ) == null
    }

}
