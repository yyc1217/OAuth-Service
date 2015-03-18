package tw.edu.ncu.cc.oauth.server.domain

import grails.persistence.Entity
import tw.edu.ncu.cc.oauth.server.domain.concern.Auditable
import tw.edu.ncu.cc.oauth.server.domain.concern.Expireable

@Entity
class AuthorizationCode implements Auditable, Expireable {

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

    static lazyAttrModes =  [
        'user'   : 'join',
        'client' : 'join',
        'scope'  : 'eager'
    ]

}
