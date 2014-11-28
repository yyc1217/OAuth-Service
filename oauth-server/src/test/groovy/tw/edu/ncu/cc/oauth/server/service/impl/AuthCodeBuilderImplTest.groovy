package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.service.AuthCodeBuilder


class AuthCodeBuilderImplTest extends SpringSpecification {

    @Autowired
    private AuthCodeBuilder authCodeBuilder

    def "it can build an AuthCodeEntity"() {
        given:
            def scope = [ "READ" ] as Set
            def clientID = 1
            def userID = "ADMIN1"
        when:
            def code = authCodeBuilder.buildAuthCode(
                    clientID, userID, scope
            )
        then:
            code.getUser().getId() == clientID
            code.getClient().getId() == clientID
    }

}
