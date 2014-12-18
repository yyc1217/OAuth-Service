package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.service.AccessTokenService
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService

import javax.persistence.NoResultException

class AccessTokenServiceImplTest extends SpringSpecification {

    @Autowired
    private AccessTokenService accessTokenService

    @Autowired
    private AuthCodeService authCodeService

    def "it can create AccessTokenEntity using permission of string set"() {
        given:
            def expireDate = new Date()
            def clientID = 1
            def userID = "ADMIN1"
            def scope  = [ "READ" ] as Set< String >
        when:
            def token = accessTokenService.createAccessToken( clientID, userID, scope, expireDate )
        then:
            token.getUser().getName() == "ADMIN1"
            token.getClient().getId() == 1
            token.getDateExpired() == expireDate
    }

    def "it can create AccessTokenEntity then revoke correspond authCode using AuthCodeEntity"() {
        given:
            def code = authCodeService.createAuthCode( 1, "ADMIN1", [ "READ" ] as Set<String> )
        when:
            def token = accessTokenService.createAccessTokenByCode( code.getCode(), null )
        then:
            token.getUser().getId() == 1
            token.getClient().getId() == 1
        when:
            authCodeService.readAuthCodeByCode( code.getCode() )
        then:
            thrown( NoResultException )
    }

    def "it can read AccessTokenEntity by id"() {
        expect:
            accessTokenService.readAccessTokenByID( "1" ).getToken() == "TOKEN1"
    }

    def "it can read AccessTokenEntity by token"() {
        expect:
            accessTokenService.readAccessTokenByToken( "Mzo6OlRPS0VO" ).getId() == 3
    }

    def "it can revoke AccessToken by id"() {
        given:
            def token = accessTokenService.createAccessToken(
                    1, "ADMIN1", [ "READ" ] as Set< String >, null
            )
        and:
            def tokenID = token.getId() as String
        when:
            accessTokenService.revokeAccessTokenByID( tokenID )
            accessTokenService.readAccessTokenByID( tokenID )
        then:
            thrown( NoResultException )
    }

    def "it can read AccessToken scope by token"() {
        when:
            def scope = accessTokenService.readTokenScopeByToken( "Mzo6OlRPS0VO" )
        then:
            scope.contains( "ADMIN" )
            scope.contains( "READ" )
    }

}
