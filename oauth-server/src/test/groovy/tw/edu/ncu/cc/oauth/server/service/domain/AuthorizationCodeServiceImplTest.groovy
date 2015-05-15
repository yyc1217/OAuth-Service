package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCodeService
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCode_
import tw.edu.ncu.cc.oauth.server.concepts.client.ClientRepository
import tw.edu.ncu.cc.oauth.server.concepts.user.UserRepository

class AuthorizationCodeServiceImplTest extends SpringSpecification {

    @Autowired
    UserRepository userRepository

    @Autowired
    ClientRepository clientRepository

    @Autowired
    AuthorizationCodeService authorizationCodeService

    @Transactional
    def "it can create authorization code"() {
        given:
            def authorizationCode = new_authorizationCode()
        when:
            def createdAuthorizationCode = authorizationCodeService.create( authorizationCode )
        and:
            def managedAuthorizationCode = authorizationCodeService.readUnexpiredById(
                    createdAuthorizationCode.id as String, AuthorizationCode_.scope
            )
        then:
            managedAuthorizationCode.client.name  == authorizationCode.client.name
            managedAuthorizationCode.user.name    == authorizationCode.user.name
            managedAuthorizationCode.scope.size() == authorizationCode.scope.size()
    }

    @Transactional
    def "it can read unexpired authorization code by real code 1"() {
        given:
            def authorizationCode = authorizationCodeService.create( new_authorizationCode() )
        expect:
            authorizationCodeService.readUnexpiredByCode( authorizationCode.code ) != null
    }

    def "it can read unexpired authorization code by real code 2"() {
        expect:
            authorizationCodeService.readUnexpiredByCode( a_authorizationCode().code ) != null
            authorizationCodeService.readUnexpiredByCode( "NOTEXIST" ) == null
    }

    def "it can read unexpired authorization codes by client id"() {
        expect:
            authorizationCodeService.readAllUnexpiredByClient( get_client( 2 ) ).size() == 0
            authorizationCodeService.readAllUnexpiredByClient( get_client( 3 ) ).size() == 1
    }

    def "it can read unexpired authorization codes by user name"() {
        expect:
            authorizationCodeService.readAllUnexpiredByUser( get_user( 2 ) ).size() == 0
            authorizationCodeService.readAllUnexpiredByUser( get_user( 3 ) ).size() == 1
    }

    @Transactional
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
            authorizationCodeService.isUnexpiredCodeMatchesClientId( code.code, serialId( code.id ) )
            ! authorizationCodeService.isUnexpiredCodeMatchesClientId( 'abc', serialId( 3 ) )
    }

}
