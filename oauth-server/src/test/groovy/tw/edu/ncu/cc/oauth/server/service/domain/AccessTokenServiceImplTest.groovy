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
            def accessToken = new AccessToken(
                    client: Client.get( 1 ),
                    user: User.get( 1 ),
                    dateExpired: laterTime(),
                    scope: [ Permission.get( 1 ) ]
            )
        when:
            def token = accessTokenService.readUnexpiredById(
                    accessTokenService.create( accessToken ).id as String, [ 'client', 'user', 'scope' ]
            )
        then:
            token.client.name == 'APP1'
            token.user.name == 'ADMIN1'
            token.scope.size() == 1
    }

    def "it can create access token from authorization code"() {
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
            def token = accessTokenService.createByAuthorizationCode(
                    new AccessToken(
                        dateExpired: laterTime()
                    ),
                    authorizationCode
            )
        then:
            authorizationCodeService.readUnexpiredByRealCode( authorizationCode.code ) == null
        and:
            AuthorizationCode.include( [ 'scope' ] ).get( authorizationCode.id ).scope.size() == 1
        and:
            token.user.name == 'ADMIN1'
            token.client.name == 'APP1'
            token.scope.size() == 1
    }

    def "it can create access token from refresh token"() {
        given:
            def accessTokenOfRefreshToken = accessTokenService.create(
                    new AccessToken(
                            client: Client.get( 1 ),
                            user: User.get( 1 ),
                            scope: [ Permission.get( 1 ) ],
                            dateExpired: laterTime()
                    )
            )
            def refreshToken = refreshTokenService.createByAccessToken(
                    new RefreshToken(
                            dateExpired: laterTime()
                    ) ,
                    accessTokenOfRefreshToken
            )
        when:
            def accessToken = accessTokenService.createByRefreshToken(
                    new AccessToken(
                        dateExpired: laterTime()
                    ),
                    refreshToken
            )
        then:
            refreshTokenService.readUnexpiredById( refreshToken.id as String, [ 'accessToken' ] ).accessToken.id != accessTokenOfRefreshToken.id
        and:
            accessToken.user.name == 'ADMIN1'
            accessToken.client.name == 'APP1'
            accessToken.scope.size() == 1
    }

    def "it can read unexpired access token by real code"() {
        expect:
            accessTokenService.readUnexpiredByRealToken( "Mzo6OlRPS0VO" ) != null
            accessTokenService.readUnexpiredByRealToken( "NOTEXIST" ) == null
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
            def accessToken = accessTokenService.create(
                    new AccessToken(
                            client: Client.get( 1 ),
                            user: User.get( 1 ),
                            scope: [ Permission.get( 1 ) ],
                            dateExpired: laterTime()
                    )
            )
        when:
            def tokenId = accessToken.id as String
        then:
            accessTokenService.readUnexpiredById( tokenId ) != null
        then:
            accessTokenService.revoke( accessTokenService.readUnexpiredById( tokenId ) ) != null
        then:
            accessTokenService.readUnexpiredById( tokenId ) == null
    }


}
