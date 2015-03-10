package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import specification.SpringSpecification
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

    def "it can create an AuthCodeEntity"() {
        given:
            def scope = [ "READ" ] as Set
            def clientID = "1"
            def userID = "ADMIN1"
            def expireDate = timeNow();
        when:
            def code = authCodeService.createAuthCode(
                    clientID, userID, scope, expireDate
            )
        then:
            code.getClient().getId() == clientID as Integer
            code.getDateExpired() == expireDate
    }

    def "it can read AuthCodeEntity by code"() {
        when:
            authCodeService.readAuthCodeByCode( "Mzo6OkNPREU=" )
        then:
            notThrown( EmptyResultDataAccessException )
    }

    def "it can revoke AuthCodeEntity by id"() {
        given:
            def code = authCodeService.createAuthCode(
                    "1", "ADMIN1", [ "READ" ] as Set, null
            )
        and:
            def codeID = code.getId() as String
        when:
            authCodeService.revokeAuthCodeByID( codeID )
            authCodeService.readAuthCodeByID( codeID )
        then:
            thrown( EmptyResultDataAccessException )
    }

}
