package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.RefreshToken

class RefreshTokenServiceImplTest extends SpringSpecification {

    @Autowired
    AccessTokenService accessTokenService

    @Autowired
    RefreshTokenService refreshTokenService

    @Autowired
    AuthorizationCodeService authorizationCodeService

    def "it can create refresh token from access token"() {
        given:
            def createdAccessToken = accessTokenService.create( new_accessToken() )
        when:
            def createdRefreshToken = refreshTokenService.createByAccessToken(
                    new RefreshToken(
                            dateExpired: laterTime()
                    ),
                    createdAccessToken
            )
        and:
            def managedRefreshToken = refreshTokenService.readUnexpiredById( createdRefreshToken.id as String, [ 'client', 'user', 'scope', 'accessToken' ] )
        then:
            managedRefreshToken.accessToken.id == createdAccessToken.id
            managedRefreshToken.user.name      == createdAccessToken.user.name
            managedRefreshToken.client.name    == createdAccessToken.client.name
            managedRefreshToken.scope.size()   == createdAccessToken.scope.size()
    }

    def "it can read unexpired refresh token by real code"() {
        expect:
            refreshTokenService.readUnexpiredByRealToken( a_refreshToken().token ) != null
            refreshTokenService.readUnexpiredByRealToken( "NOTEXIST" ) == null
    }

    def "it can read unexpired refresh tokens by client id"() {
        expect:
            refreshTokenService.readAllUnexpiredByClient( Client.get( 2 ) ).size() == 0
            refreshTokenService.readAllUnexpiredByClient( Client.get( 3 ) ).size() == 1
    }

    def "it can revoke refresh token"() {
        given:
            def createdAccessToken  = accessTokenService.create( new_accessToken() )
            def createdRefreshToken = refreshTokenService.createByAccessToken(
                    new RefreshToken(
                            dateExpired: laterTime()
                    ),
                    createdAccessToken
            )
        and:
            def refreshTokenId = createdRefreshToken.id as String
        expect:
            refreshTokenService.readUnexpiredById( refreshTokenId ) != null
        when:
            refreshTokenService.revoke( refreshTokenService.readUnexpiredById( refreshTokenId ) ) != null
        then:
            refreshTokenService.readUnexpiredById( refreshTokenId ) == null
    }

    def "it can check if refresh token is unexpired and binded with specified client"() {
        given:
            def refreshToken = a_refreshToken()
        expect:
            refreshTokenService.isTokenUnexpiredWithClientId( refreshToken.token, serialId( refreshToken.id ) )
            ! refreshTokenService.isTokenUnexpiredWithClientId( 'abc', serialId( 3 ) )
    }

}
