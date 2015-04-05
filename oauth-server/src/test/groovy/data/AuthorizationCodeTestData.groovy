package data

import tw.edu.ncu.cc.oauth.server.domain.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.Permission
import tw.edu.ncu.cc.oauth.server.domain.User


trait AuthorizationCodeTestData extends DomainTestData {

    static AuthorizationCode new_authorizationCode() {
        new AuthorizationCode(
                client: Client.get( 1 ),
                user: User.get( 1 ),
                scope: [ Permission.get( 1 ) ],
                dateExpired: laterTime()
        )
    }

    static AuthorizationCode a_authorizationCode() {
        new AuthorizationCode(
                id: 3,
                code: "Mzo6OkNPREU=",
                client: Client.get( 3 ),
                user: User.get( 3 ),
                scope: [ Permission.get( 3 ) ],
                dateExpired: laterTime()
        )
    }

}