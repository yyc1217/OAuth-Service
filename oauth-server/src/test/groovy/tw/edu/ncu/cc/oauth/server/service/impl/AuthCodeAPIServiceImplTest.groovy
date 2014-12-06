package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.service.AuthCodeAPIService


class AuthCodeAPIServiceImplTest extends SpringSpecification {

    @Autowired
    private AuthCodeAPIService authCodeAPIService

    def "it can create an AuthCodeEntity"() {
        given:
            def scope = [ "READ" ] as Set
            def clientID = 1
            def userID = "ADMIN1"
        when:
            def code = authCodeAPIService.createAuthCode(
                    clientID, userID, scope
            )
        then:
            code.getUser().getId() == clientID
            code.getClient().getId() == clientID
    }

    def "it can read AuthCodeEntity by id"() {
        when:
            def code = authCodeAPIService.readAuthCodeByID( "1" )
        then:
            code.getCode() == "CODE1"
    }

    def "it can revoke AuthCodeEntity by id"() {
        given:
            def code = authCodeAPIService.createAuthCode(
                    1, "ADMIN1", [ "READ" ] as Set
            )
        and:
            def codeID = code.getId() as String
        when:
            authCodeAPIService.revokeAuthCodeByID( codeID )
        then:
            authCodeAPIService.readAuthCodeByID( codeID ).getDateExpired().before( timeNow() )
    }

}
