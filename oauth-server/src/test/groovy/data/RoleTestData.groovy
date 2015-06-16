package data

import tw.edu.ncu.cc.oauth.server.concepts.role.Role

trait RoleTestData extends DomainTestData {

    Role get_role( int id ) {
        getRoles().findOne( id )
    }

}