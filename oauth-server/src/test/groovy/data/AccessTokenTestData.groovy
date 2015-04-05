package data

import tw.edu.ncu.cc.oauth.server.domain.AccessToken
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.Permission
import tw.edu.ncu.cc.oauth.server.domain.User

trait AccessTokenTestData extends DomainTestData {

    static AccessToken new_accessToken(){
        new AccessToken(
                client: Client.get( 1 ),
                user: User.get( 1 ),
                scope: [ Permission.get( 1 ) ],
                dateExpired: laterTime()
        )
    }

    static AccessToken a_accessToken() {
        new AccessToken(
                id: 3,
                client: Client.get( 3 ),
                user: User.get( 3 ),
                scope: [ Permission.get( 1 ), Permission.get( 2 ) ],
                token: "Mzo6OlRPS0VO"
        )
    }

    static AccessToken get_accessToken( long id ) {
        AccessToken.include( [ 'client', 'user', 'scope' ] ).get( id )
    }

}