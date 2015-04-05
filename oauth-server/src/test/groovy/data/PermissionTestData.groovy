package data

import tw.edu.ncu.cc.oauth.server.domain.Permission

trait PermissionTestData extends DomainTestData {

    static Permission a_permission() {
        new Permission(
                id: 1,
                name: "ADMIN"
        )
    }

}