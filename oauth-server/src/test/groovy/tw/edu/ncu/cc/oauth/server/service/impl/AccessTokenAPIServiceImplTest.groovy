package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.service.AccessTokenAPIService
import tw.edu.ncu.cc.oauth.server.service.AuthCodeAPIService
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService

class AccessTokenAPIServiceImplTest extends SpringSpecification {

    @Autowired
    private AccessTokenAPIService accessTokenAPIService

    @Autowired
    private AuthCodeAPIService authCodeBuilder

    @Autowired
    private AuthCodeService authCodeService

    def "it can create AccessTokenEntity using permission of string set"() {
        given:
            def clientID = 1
            def userID = "ADMIN1"
            def scope  = [ "READ" ] as Set< String >
        when:
            def token = accessTokenAPIService.createAccessToken( clientID, userID, scope )
        then:
            token.getUser().getName() == "ADMIN1"
            token.getClient().getId() == 1
    }

    def "it can create AccessTokenEntity then delete correspond authCode using AuthCodeEntity"() {
        given:
            def code = authCodeBuilder.createAuthCode( 1, "ADMIN1", [ "READ" ] as Set<String> )
        when:
            def token = accessTokenAPIService.createAccessTokenByCode( code.getCode() )
        then:
            token.getUser().getId() == 1
            token.getClient().getId() == 1
            authCodeService.readAuthCode( code.getId() ) == null
    }

    def "it can read AccessTokenEntity by id"() {
        expect:
            accessTokenAPIService.readAccessTokenByID( "1" ).getToken() == "TOKEN1"
    }

    def "it can read AccessTokenEntity by token"() {
        expect:
            accessTokenAPIService.readAccessTokenByToken( "Mzo6OlRPS0VO" ).getId() == 3
    }

    def "it can delete AccessToken by id"() {
        given:
            def token = accessTokenAPIService.createAccessToken(
                    1, "ADMIN1", [ "READ" ] as Set< String >
            )
        when:
            accessTokenAPIService.deleteAccessTokenByID( token.getId() as String )
        then:
            accessTokenAPIService.readAccessTokenByID( token.getId() as String ) == null
    }

    def "it can read AccessToken scope by token"() {
        when:
            def scope = accessTokenAPIService.readTokenScopeByToken( "Mzo6OlRPS0VO" )
        then:
            scope.contains( "ADMIN" )
            scope.contains( "READ" )
    }

}
