package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.User

class AuthorizationCodeServiceImplTest extends SpringSpecification {

    @Autowired
    private AuthorizationCodeService authorizationCodeService

    def "it can create authorization code"() {
        given:
            def authorizationCode = new_authorizationCode()
        when:
            def createdAuthorizationCode = authorizationCodeService.create( authorizationCode )
        and:
            def managedAuthorizationCode = authorizationCodeService.readUnexpiredById(
                    createdAuthorizationCode.id as String, [ 'client', 'user', 'scope' ]
            )
        then:
            managedAuthorizationCode.client.name  == authorizationCode.client.name
            managedAuthorizationCode.user.name    == authorizationCode.user.name
            managedAuthorizationCode.scope.size() == authorizationCode.scope.size()
    }

    def "it can read unexpired authorization code by real code 1"() {
        given:
            def authorizationCode = authorizationCodeService.create( new_authorizationCode() )
        expect:
            authorizationCodeService.readUnexpiredByRealCode( authorizationCode.code ) != null
    }

    def "it can read unexpired authorization code by real code 2"() {
        expect:
            authorizationCodeService.readUnexpiredByRealCode( a_authorizationCode().code ) != null
            authorizationCodeService.readUnexpiredByRealCode( "NOTEXIST" ) == null
    }

    def "it can read unexpired authorization codes by client id"() {
        expect:
            authorizationCodeService.readAllUnexpiredByClient( Client.get( 2 ) ).size() == 0
            authorizationCodeService.readAllUnexpiredByClient( Client.get( 3 ) ).size() == 1
    }

    def "it can read unexpired authorization codes by user name"() {
        expect:
            authorizationCodeService.readAllUnexpiredByUser( User.get( 2 ) ).size() == 0
            authorizationCodeService.readAllUnexpiredByUser( User.get( 3 ) ).size() == 1
    }

    def "it can revoke authorization code"() {
        given:
            def authorizationCode = new_authorizationCode()
        and:
            def createdAuthorizationCodeId = authorizationCodeService.create( authorizationCode ).id as String
        when:
            authorizationCodeService.revoke( authorizationCodeService.readUnexpiredById( createdAuthorizationCodeId ) )
        then:
            authorizationCodeService.readUnexpiredById( createdAuthorizationCodeId ) == null
    }

    def "it can check if authorization code is unexpired and binded with specified client"() {
        given:
            def code = a_authorizationCode()
        expect:
            authorizationCodeService.isCodeUnexpiredWithClientId( code.code, serialId( code.id ) )
            ! authorizationCodeService.isCodeUnexpiredWithClientId( 'abc', serialId( 3 ) )
    }

}
