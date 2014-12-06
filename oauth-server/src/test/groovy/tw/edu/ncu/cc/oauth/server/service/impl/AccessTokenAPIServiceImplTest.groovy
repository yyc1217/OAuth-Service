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
    private AuthCodeAPIService authCodeAPIService

    @Autowired
    private AuthCodeService authCodeService

    def "it can create AccessTokenEntity using permission of string set"() {
        given:
            def expireDate = new Date()
            def clientID = 1
            def userID = "ADMIN1"
            def scope  = [ "READ" ] as Set< String >
        when:
            def token = accessTokenAPIService.createAccessToken( clientID, userID, scope, expireDate )
        then:
            token.getUser().getName() == "ADMIN1"
            token.getClient().getId() == 1
            token.getDateExpired() == expireDate
    }

    def "it can create AccessTokenEntity then revoke correspond authCode using AuthCodeEntity"() {
        given:
            def code = authCodeAPIService.createAuthCode( 1, "ADMIN1", [ "READ" ] as Set<String> )
        when:
            def token = accessTokenAPIService.createAccessTokenByCode( code.getCode(), null )
        then:
            token.getUser().getId() == 1
            token.getClient().getId() == 1
            authCodeService.readAuthCode( code.getCode() ) == null
    }

    def "it can read AccessTokenEntity by id"() {
        expect:
            accessTokenAPIService.readAccessTokenByID( "1" ).getToken() == "TOKEN1"
    }

    def "it can read AccessTokenEntity by token"() {
        expect:
            accessTokenAPIService.readAccessTokenByToken( "Mzo6OlRPS0VO" ).getId() == 3
    }

    def "it can revoke AccessToken by id"() {
        given:
            def token = accessTokenAPIService.createAccessToken(
                    1, "ADMIN1", [ "READ" ] as Set< String >, null
            )
        and:
            def tokenID = token.getId() as String
        when:
            accessTokenAPIService.revokeAccessTokenByID( tokenID )
        then:
            accessTokenAPIService.readAccessTokenByID( tokenID ).getDateExpired().before( timeNow() )
    }

    def "it can read AccessToken scope by token"() {
        when:
            def scope = accessTokenAPIService.readTokenScopeByToken( "Mzo6OlRPS0VO" )
        then:
            scope.contains( "ADMIN" )
            scope.contains( "READ" )
    }

}
