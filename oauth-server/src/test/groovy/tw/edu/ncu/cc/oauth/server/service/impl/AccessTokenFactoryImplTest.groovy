package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.service.AccessTokenFactory
import tw.edu.ncu.cc.oauth.server.service.AuthCodeFactory
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService

class AccessTokenFactoryImplTest extends SpringSpecification {

    @Autowired
    private AccessTokenFactory accessTokenBuilder

    @Autowired
    private AuthCodeFactory authCodeBuilder

    @Autowired
    private AuthCodeService authCodeService

    def "it can build AccessToken using permission of string set"() {
        given:
            def clientID = 1
            def userID = "ADMIN1"
            def scope  = [ "READ" ] as Set< String >
        when:
            def token = accessTokenBuilder.createAccessToken( clientID, userID, scope )
        then:
            token.getUser().getName() == "ADMIN1"
            token.getClient().getId() == 1
    }

    def "it can build AccessToken then delete correspond authCode using AuthCodeEntity"() {
        given:
            def code = authCodeBuilder.createAuthCode( 1, "ADMIN1", [ "READ" ] as Set<String> )
        when:
            def token = accessTokenBuilder.createAccessToken( code.getCode() )
        then:
            token.getUser().getId() == 1
            token.getClient().getId() == 1
            authCodeService.getAuthCode( code.getId() ) == null
    }

}
