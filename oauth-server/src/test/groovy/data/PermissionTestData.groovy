package data

import tw.edu.ncu.cc.oauth.server.concepts.permission.Permission

trait PermissionTestData extends DomainTestData {

    Permission a_permission() {
        new Permission(
                id: 1,
                name: "ADMIN"
        )
    }

    Permission get_permission( int id ) {
        getPermissions().findOne( id )
    }

}