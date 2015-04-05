package data

import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.Permission
import tw.edu.ncu.cc.oauth.server.domain.RefreshToken
import tw.edu.ncu.cc.oauth.server.domain.User

trait RefreshTokenTestData extends DomainTestData {

    static RefreshToken a_refreshToken() {
        new RefreshToken(
                id: 3,
                client: Client.get( 3 ),
                user: User.get( 3 ),
                scope: [ Permission.get( 1 ), Permission.get( 2 ) ],
                token: "Mzo6OlRPS0VO"
        )
    }

}