package tw.edu.ncu.cc.oauth.server.domain

import grails.persistence.Entity
import tw.edu.ncu.cc.oauth.server.domain.concern.Auditable
import tw.edu.ncu.cc.oauth.server.domain.concern.Expireable

@Entity
class ApiToken implements Auditable, Expireable {

    String token

    static belongsTo = [
        client:Client
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
