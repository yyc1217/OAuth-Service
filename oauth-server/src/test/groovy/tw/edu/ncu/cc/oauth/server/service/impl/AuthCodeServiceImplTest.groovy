package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService
import tw.edu.ncu.cc.oauth.server.service.ClientService
import tw.edu.ncu.cc.oauth.server.service.UserService

import javax.persistence.NoResultException

class AuthCodeServiceImplTest extends SpringSpecification {

    @Autowired
    private AuthCodeService authCodeService

    @Autowired
    private ClientService clientService

    @Autowired
    private UserService userService

    def "it can create an AuthCodeEntity"() {
        given:
            def scope = [ "READ" ] as Set
            def clientID = "1"
            def userID = "ADMIN1"
        when:
            def code = authCodeService.createAuthCode(
                    clientID, userID, scope
            )
        then:
            code.getClient().getId() == clientID as Integer
    }

    def "it can read AuthCodeEntity by code"() {
        when:
            authCodeService.readAuthCodeByCode( "Mzo6OkNPREU=" )
        then:
            notThrown( NoResultException )
    }

    def "it can revoke AuthCodeEntity by id"() {
        given:
            def code = authCodeService.createAuthCode(
                    "1", "ADMIN1", [ "READ" ] as Set
            )
        and:
            def codeID = code.getId() as String
        when:
            authCodeService.revokeAuthCodeByID( codeID )
            authCodeService.readAuthCodeByID( codeID )
        then:
            thrown( NoResultException )
    }

}
