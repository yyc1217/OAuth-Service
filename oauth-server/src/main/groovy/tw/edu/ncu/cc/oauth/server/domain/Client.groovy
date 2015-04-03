package tw.edu.ncu.cc.oauth.server.domain

import grails.persistence.Entity
import tw.edu.ncu.cc.oauth.server.domain.concern.Auditable

@Entity
class Client implements Auditable {

    String secret
    String name
    String description
    String url
    String callback
    int apiUseTimes

    static belongsTo = [ owner:User ]

    static hasMany = [
        accessTokens:AccessToken,
        codes:AuthorizationCode,
        refreshTokens: RefreshToken
    ]

    static constraints = {
        description nullable: true, blank:true
        url nullable: true, blank: true
    }

    static namedQueries = {
        include { attrs ->
            attrs.each {
                join it
            }
        }
    }

}
