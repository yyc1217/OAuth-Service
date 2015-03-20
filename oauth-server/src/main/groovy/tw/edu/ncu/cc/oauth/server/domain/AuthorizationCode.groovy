package tw.edu.ncu.cc.oauth.server.domain

import grails.persistence.Entity
import tw.edu.ncu.cc.oauth.server.domain.concern.Auditable
import tw.edu.ncu.cc.oauth.server.domain.concern.Expireable
import tw.edu.ncu.cc.oauth.server.domain.concern.Lazyable

@Entity
class AuthorizationCode implements Auditable, Expireable, Lazyable {

    String code

    static belongsTo = [
        user:User,
        client:Client
    ]

    static hasMany = [
        scope:Permission
    ]

    static constraints = {
        code unique: true
    }

    static unexpired = where {
        dateExpired > timeNow()
    }

    static attrFetchModes =  [
        'user'   : 'join',
        'client' : 'join',
        'scope'  : 'eager'
    ]

}
