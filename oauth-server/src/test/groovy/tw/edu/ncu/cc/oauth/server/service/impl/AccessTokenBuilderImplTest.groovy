package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.service.AccessTokenBuilder
import tw.edu.ncu.cc.oauth.server.service.AuthCodeBuilder
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService

class AccessTokenBuilderImplTest extends SpringSpecification {

    @Autowired
    private AccessTokenBuilder accessTokenBuilder

    @Autowired
    private AuthCodeBuilder authCodeBuilder

    @Autowired
    private AuthCodeService authCodeService

    def "it can build AccessToken using permission of string set"() {
        given:
            def clientID = 1
            def userID = "ADMIN1"
            def scope  = [ "READ" ] as Set< String >
        when:
            def token = accessTokenBuilder.buildAccessToken( clientID, userID, scope )
        then:
            token.getUser().getName() == "ADMIN1"
            token.getClient().getId() == 1
    }

    def "it can build AccessToken then delete correspond authCode using AuthCodeEntity"() {
        given:
            def code = authCodeBuilder.buildAuthCode( 1, "ADMIN1", [ "READ" ] as Set<String> )
        when:
            def token = accessTokenBuilder.buildAccessToken( code.getCode() )
        then:
            token.getUser().getId() == 1
            token.getClient().getId() == 1
            authCodeService.getAuthCode( code.getId() ) == null
    }

}
