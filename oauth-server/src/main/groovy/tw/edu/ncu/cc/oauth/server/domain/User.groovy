package tw.edu.ncu.cc.oauth.server.domain

import grails.persistence.Entity
import tw.edu.ncu.cc.oauth.server.domain.concern.Auditable

@Entity
class User implements Auditable {

    String name

    static hasMany = [
        tokens: AccessToken ,
        codes: AuthorizationCode,
        clients: Client
    ]

    static namedQueries = {
        include { attrs ->
            attrs.each {
                join it
            }
        }
    }

}
