package data

import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCode

trait AuthorizationCodeTestData extends DomainTestData {

    AuthorizationCode new_authorizationCode() {
        new AuthorizationCode(
                client: getClients().findOne( 1 ),
                user: getUsers().findOne( 1 ),
                scope: [ getPermissions().findOne( 1 ) ],
                dateExpired: laterTime()
        )
    }

    AuthorizationCode a_authorizationCode() {
        new AuthorizationCode(
                id: 3,
                code: "Mzo6OkNPREU=",
                client: getClients().findOne( 3 ),
                user: getUsers().findOne( 3 ),
                scope: [ getPermissions().findOne( 3 ) ],
                dateExpired: laterTime()
        )
    }

}