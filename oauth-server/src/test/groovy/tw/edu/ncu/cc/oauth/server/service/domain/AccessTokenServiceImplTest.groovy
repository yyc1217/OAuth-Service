package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessToken
import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessTokenService
import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessToken_
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCodeService
import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshToken
import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshTokenService

class AccessTokenServiceImplTest extends SpringSpecification {

    @Autowired
    AccessTokenService accessTokenService

    @Autowired
    RefreshTokenService refreshTokenService

    @Autowired
    AuthorizationCodeService authorizationCodeService

    @Transactional
    def "it can create access token"() {
        given:
            def accessToken = new_accessToken()
        when:
            def createdAccessToken = accessTokenService.create( accessToken )
        and:
            def managedAccessToken = accessTokenService.findUnexpiredById(
                    createdAccessToken.id as String, AccessToken_.scope
            )
        then:
            createdAccessToken.client.name  == accessToken.client.name
            createdAccessToken.user.name    == accessToken.user.name
            createdAccessToken.scope.size() == accessToken.scope.size()
        and:
            managedAccessToken.client.name  == accessToken.client.name
            managedAccessToken.user.name    == accessToken.user.name
            managedAccessToken.scope.size() == accessToken.scope.size()
        and:
            createdAccessToken.token != null
    }

    @Transactional
    def "it can create access token from authorization code"() {
        given:
            def authorizationCode = new_authorizationCode()
        and:
            def createdAuthorizationCode = authorizationCodeService.create( authorizationCode )
        when:
            def createdAccessToken = accessTokenService.createByAuthorizationCode(
                    new AccessToken(
                        dateExpired: laterTime()
                    ),
                    authorizationCode
            )
        then:
            authorizationCode_is_revoked( createdAuthorizationCode )
        and:
            createdAccessToken.user.name    == authorizationCode.user.name
            createdAccessToken.client.name  == authorizationCode.client.name
            createdAccessToken.scope.size() == authorizationCode.scope.size()
    }

    @Transactional
    def "it can create access token from refresh token"() {
        given:
            def accessToken = new_accessToken()
            def createdAccessToken1 = accessTokenService.create( accessToken )
            def refreshToken = refreshTokenService.createByAccessToken(
                    new RefreshToken(
                            dateExpired: laterTime()
                    ) ,
                    createdAccessToken1
            )
        when:
            def createdAccessToken2 = accessTokenService.createByRefreshToken(
                    new AccessToken(
                        dateExpired: laterTime()
                    ),
                    refreshToken
            )
        then:
            accessToken_of_refreshToken_inDB_notEquals_to_the_accessToken( refreshToken, createdAccessToken1 )
        and:
            createdAccessToken2.user.name    == refreshToken.user.name
            createdAccessToken2.client.name  == refreshToken.client.name
            createdAccessToken2.scope.size() == refreshToken.scope.size()
    }

    def "it can read unexpired access token by real code"() {
        given:
            def accessToken = a_accessToken()
        expect:
            accessTokenService.findUnexpiredByToken( accessToken.token ) != null
            accessTokenService.findUnexpiredByToken( "NOTEXIST" ) == null
        when:
            def token1 = accessTokenService.findUnexpiredByToken( accessToken.token )
            def token2 = accessTokenService.findUnexpiredByToken( accessToken.token )
        then:
            token1.lastUpdated != token2.lastUpdated
    }

    def "it can read unexpired access tokens by client id"() {
        expect:
            accessTokenService.findAllUnexpiredByClient( get_client( 2 ) ).size() == 0
            accessTokenService.findAllUnexpiredByClient( get_client( 3 ) ).size() == 1
    }

    def "it can read unexpired access tokens by user name"() {
        expect:
            accessTokenService.findAllUnexpiredByUser( get_user( 2 ) ).size() == 0
            accessTokenService.findAllUnexpiredByUser( get_user( 3 ) ).size() == 1
    }

    @Transactional
    def "it can revoke access token"() {
        given:
            def createdAccessToken = accessTokenService.create( new_accessToken() )
            def managedAccessToken = accessTokenService.findUnexpiredById( createdAccessToken.id as String )
        expect:
            ! accessToken_is_revoked( createdAccessToken )
        when:
            accessTokenService.revoke( managedAccessToken )
        then:
            accessToken_is_revoked( createdAccessToken )
    }

    private def authorizationCode_is_revoked( AuthorizationCode authorizationCode ) {
        authorizationCodeService.findUnexpiredByCode( authorizationCode.code ) == null
    }

    private def accessToken_of_refreshToken_inDB_notEquals_to_the_accessToken( refreshToken, accessToken ) {
        refreshTokenService.findUnexpiredById( refreshToken.id as String ).accessToken.id != accessToken.id
    }

    private def accessToken_is_revoked( AccessToken accessToken ) {
        accessTokenService.findUnexpiredById( accessToken.id as String ) == null
    }

}
