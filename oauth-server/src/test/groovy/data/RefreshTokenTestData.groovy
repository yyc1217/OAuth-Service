package data

import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshToken

trait RefreshTokenTestData extends DomainTestData {

    RefreshToken a_refreshToken() {
        new RefreshToken(
                id: 3,
                client: getClients().findOne( 3 ),
                user: getUsers().findOne( 3 ),
                scope: [ getPermissions().findOne( 1 ), getPermissions().findOne( 3 ) ],
                encryptedToken: "Mzo6OlRPS0VO"
        )
    }

}