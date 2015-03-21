package tw.edu.ncu.cc.oauth.server.domain

import grails.persistence.Entity
import tw.edu.ncu.cc.oauth.server.domain.concern.Auditable
import tw.edu.ncu.cc.oauth.server.domain.concern.Expireable

@Entity
public class AccessToken implements Auditable, Expireable {

    String token

    static belongsTo = [
        user:User,
        client:Client
    ]

    static hasMany = [
        scope:Permission
    ]

    static constraints = {
        token unique: true
    }

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
