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

    static namedQueries = {
        unexpired {
            gt 'dateExpired' , this.timeNow()
        }
        include { attrs ->
            attrs.each {
                join it
            }
        }
    }

}
