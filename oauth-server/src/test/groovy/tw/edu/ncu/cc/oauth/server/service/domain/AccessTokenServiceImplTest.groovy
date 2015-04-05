package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.domain.*

class AccessTokenServiceImplTest extends SpringSpecification {

    @Autowired
    AccessTokenService accessTokenService

    @Autowired
    RefreshTokenService refreshTokenService

    @Autowired
    AuthorizationCodeService authorizationCodeService

    def "it can create access token"() {
        given:
            def accessToken = new_accessToken()
        when:
            def createdAccessToken = accessTokenService.create( accessToken )
        and:
            def managedAccessToken = accessTokenService.readUnexpiredById(
                    createdAccessToken.id as String, [ 'client', 'user', 'scope' ]
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
            createdAccessToken.token != managedAccessToken.token
    }

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
            accessTokenService.readAndUseUnexpiredByRealToken( accessToken.token ) != null
            accessTokenService.readAndUseUnexpiredByRealToken( "NOTEXIST" ) == null
        when:
            def token1 = accessTokenService.readAndUseUnexpiredByRealToken( accessToken.token )
            def token2 = accessTokenService.readAndUseUnexpiredByRealToken( accessToken.token )
        then:
            token1.lastUpdated != token2.lastUpdated
    }

    def "it can read unexpired access tokens by client id"() {
        expect:
            accessTokenService.readAllUnexpiredByClient( Client.get( 2 ) ).size() == 0
            accessTokenService.readAllUnexpiredByClient( Client.get( 3 ) ).size() == 1
    }

    def "it can read unexpired access tokens by user name"() {
        expect:
            accessTokenService.readAllUnexpiredByUser( User.get( 2 ) ).size() == 0
            accessTokenService.readAllUnexpiredByUser( User.get( 3 ) ).size() == 1
    }

    def "it can revoke access token"() {
        given:
            def createdAccessToken = accessTokenService.create( new_accessToken() )
            def managedAccessToken = accessTokenService.readUnexpiredById( createdAccessToken.id as String )
        expect:
            ! accessToken_is_revoked( createdAccessToken )
        when:
            accessTokenService.revoke( managedAccessToken )
        then:
            accessToken_is_revoked( createdAccessToken )
    }

    private def authorizationCode_is_revoked( AuthorizationCode authorizationCode ) {
        authorizationCodeService.readUnexpiredByRealCode( authorizationCode.code ) == null
    }

    private def accessToken_of_refreshToken_inDB_notEquals_to_the_accessToken( refreshToken, accessToken ) {
        refreshTokenService.readUnexpiredById( refreshToken.id as String, [ 'accessToken' ] ).accessToken.id != accessToken.id
    }

    private def accessToken_is_revoked( AccessToken accessToken ) {
        accessTokenService.readUnexpiredById( accessToken.id as String ) == null
    }

}
