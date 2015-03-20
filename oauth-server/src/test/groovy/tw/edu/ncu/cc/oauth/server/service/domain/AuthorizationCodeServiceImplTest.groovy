package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.domain.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.Permission
import tw.edu.ncu.cc.oauth.server.domain.User


class AuthorizationCodeServiceImplTest extends SpringSpecification {

    @Autowired
    private AuthorizationCodeService authorizationCodeService

    def "it can create authorization code"() {
        given:
            def authorizationCode = new AuthorizationCode(
                    client: Client.get( 1 ),
                    user: User.get( 1 ),
                    scope: [ Permission.get( 1 ) ],
                    dateExpired: laterTime()
            )
        when:
            def code = authorizationCodeService.readUnexpiredById(
                    authorizationCodeService.create( authorizationCode ).id as String, [ 'client', 'user', 'scope' ]
            )
        then:
            code.client.name == 'APP1'
            code.user.name == 'ADMIN1'
            code.scope.size() == 1
    }

    def "it can read unexpired authorization code by real code 1"() {
        given:
            def authorizationCode = authorizationCodeService.create( new AuthorizationCode(
                    client: Client.get( 1 ),
                    user: User.get( 1 ),
                    scope: [ Permission.get( 1 ) ],
                    dateExpired: laterTime()
            ) )
        expect:
            authorizationCodeService.readUnexpiredByRealCode( authorizationCode.code ) != null
    }

    def "it can read unexpired authorization code by real code 2"() {
        expect:
            authorizationCodeService.readUnexpiredByRealCode( "Mzo6OkNPREU=" ) != null
            authorizationCodeService.readUnexpiredByRealCode( "NOTEXIST" ) == null
    }

    def "it can read unexpired authorization codes by client id"() {
        expect:
            authorizationCodeService.readAllUnexpiredByClientId( '2' ).size() == 0
            authorizationCodeService.readAllUnexpiredByClientId( '3' ).size() == 1
    }

    def "it can read unexpired authorization codes by user name"() {
        expect:
            authorizationCodeService.readAllUnexpiredByUserName( 'ADMIN2' ).size() == 0
            authorizationCodeService.readAllUnexpiredByUserName( 'ADMIN3' ).size() == 1
    }

    def "it can revoke authorization code by id"() {
        given:
            def authorizationCode = authorizationCodeService.create(
                    new AuthorizationCode(
                            client: Client.get( 1 ),
                            user: User.get( 1 ),
                            scope: [ Permission.get( 1 ) ],
                            dateExpired: laterTime()
                    )
            )
        when:
            def codeId = authorizationCode.id as String
        and:
            authorizationCodeService.revoke( authorizationCodeService.readUnexpiredById( codeId ) )
        then:
            authorizationCodeService.readUnexpiredById( codeId ) == null
    }

    def "it can check if authorization code is unexpired and binded with specified client"() {
        expect:
            authorizationCodeService.isCodeUnexpiredWithClientId( 'Mzo6OkNPREU=', serialId( 3 ) )
            ! authorizationCodeService.isCodeUnexpiredWithClientId( 'abc', serialId( 3 ) )
    }

}
