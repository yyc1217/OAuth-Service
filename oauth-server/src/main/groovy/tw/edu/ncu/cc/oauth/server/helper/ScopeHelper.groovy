package tw.edu.ncu.cc.oauth.server.helper

import tw.edu.ncu.cc.oauth.server.domain.Permission


class ScopeHelper {

    static String[] toStringArray( Set< Permission > scope ) {
        return scope.inject( [], { set, permission ->
            set << permission.name
        } ) as String[]
    }

}
