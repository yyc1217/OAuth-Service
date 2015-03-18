package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.domain.*

class AccessTokenServiceImplTest extends SpringSpecification {

    @Autowired
    AccessTokenService accessTokenService

    @Autowired
    AuthorizationCodeService authorizationCodeService

    def "it can create access token"() {
        given:
            def accessToken = new AccessToken()
            accessToken.client = Client.get( 1 )
            accessToken.user = User.get( 1 )
            accessToken.dateExpired = laterTime()
            accessToken.scope = [ Permission.get( 1 ) ]
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
        when:
            def authorizationCode = authorizationCodeService.create(
                    new AuthorizationCode(
                        client: Client.get( 1 ),
                        user: User.get( 1 ),
                        scope: [ Permission.get( 1 ) ],
                        dateExpired: laterTime()
                    )
            )
        then:
            authorizationCodeService.readUnexpiredByRealCode( authorizationCode.code ) != null
        when:
            def token = accessTokenService.createByCode( new AccessToken(
                    dateExpired: laterTime()
            ), authorizationCode.code )
        then:
            authorizationCodeService.readUnexpiredByRealCode( authorizationCode.code ) == null
        and:
            token.user.name == 'ADMIN1'
            token.client.name == 'APP1'
    }

    def "it can read unexpired access token by real code"() {
        expect:
            accessTokenService.readUnexpiredByRealToken( "Mzo6OlRPS0VO" ) != null
            accessTokenService.readUnexpiredByRealToken( "NOTEXIST" ) == null
    }

    def "it can read unexpired access tokens by client id"() {
        expect:
            accessTokenService.readAllUnexpiredByClientId( '2' ).size() == 0
            accessTokenService.readAllUnexpiredByClientId( '3' ).size() == 1
    }

    def "it can read unexpired access tokens by user name"() {
        expect:
            accessTokenService.readAllUnexpiredByUserName( 'ADMIN2' ).size() == 0
            accessTokenService.readAllUnexpiredByUserName( 'ADMIN3' ).size() == 1
    }

    def "it can revoke access token by id"() {
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
