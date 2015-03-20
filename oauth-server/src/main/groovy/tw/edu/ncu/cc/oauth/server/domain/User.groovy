package tw.edu.ncu.cc.oauth.server.domain

import grails.persistence.Entity
import tw.edu.ncu.cc.oauth.server.domain.concern.Auditable
import tw.edu.ncu.cc.oauth.server.domain.concern.Lazyable

@Entity
class User implements Auditable, Lazyable {

    String name

    static hasMany = [
        tokens: AccessToken ,
        codes: AuthorizationCode,
        clients: Client
    ]

    static attrFetchModes = [
        'tokens'  : 'eager',
        'codes'   : 'eager',
        'clients' : 'eager'
    ]

}
