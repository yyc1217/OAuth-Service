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

    def "it can create refresh token from attributes of access token"() {
        when:
            def refreshToken = refreshTokenService.createByAccessToken(
                    new RefreshToken(
                            dateExpired: laterTime()
                    ),
                    accessTokenService.readUnexpiredById( "1", [ 'client', 'user', 'scope' ] )
            )
        then:
            refreshToken.user.name == 'ADMIN1'
            refreshToken.client.name == 'APP1'
            refreshToken.accessToken.id == 1
    }

    def "it can read unexpired refresh token by real code"() {
        expect:
            refreshTokenService.readUnexpiredByRealToken( "Mzo6OlRPS0VO" ) != null
            refreshTokenService.readUnexpiredByRealToken( "NOTEXIST" ) == null
    }

    def "it can read unexpired refresh tokens by client id"() {
        expect:
            refreshTokenService.readAllUnexpiredByClient( Client.get( 2 ) ).size() == 0
            refreshTokenService.readAllUnexpiredByClient( Client.get( 3 ) ).size() == 1
    }

    def "it can revoke refresh token"() {
        given:
            def refreshToken = refreshTokenService.createByAccessToken(
                    new RefreshToken(
                            dateExpired: laterTime()
                    ),
                    accessTokenService.readUnexpiredById( "1", [ 'client', 'user', 'scope' ] )
            )
        when:
            def refreshTokenId = refreshToken.id as String
        then:
            refreshTokenService.readUnexpiredById( refreshTokenId ) != null
        then:
            refreshTokenService.revoke( refreshTokenService.readUnexpiredById( refreshTokenId ) ) != null
        then:
            refreshTokenService.readUnexpiredById( refreshTokenId ) == null
    }

    def "it can check if refresh token is unexpired and binded with specified client"() {
        expect:
            refreshTokenService.isTokenUnexpiredWithClientId( 'Mzo6OlRPS0VO', serialId( 3 ) )
            ! refreshTokenService.isTokenUnexpiredWithClientId( 'abc', serialId( 3 ) )
    }

}
