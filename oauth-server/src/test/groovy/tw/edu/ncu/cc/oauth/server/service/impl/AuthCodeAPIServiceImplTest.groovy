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

    def "it can delete AuthCodeEntity by id"() {
        given:
            def code = authCodeAPIService.createAuthCode(
                    1, "ADMIN1", [ "READ" ] as Set
            )
        when:
            authCodeAPIService.deleteAuthCodeByID( code.getId() as String )
        then:
            authCodeAPIService.readAuthCodeByID( code.getId() as String ) == null
    }

}
