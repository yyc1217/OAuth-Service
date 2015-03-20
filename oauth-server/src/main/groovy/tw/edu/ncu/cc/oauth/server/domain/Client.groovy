package tw.edu.ncu.cc.oauth.server.domain

import grails.persistence.Entity
import tw.edu.ncu.cc.oauth.server.domain.concern.Auditable
import tw.edu.ncu.cc.oauth.server.domain.concern.Lazyable

@Entity
class Client implements Auditable, Lazyable {

    String secret
    String name
    String description
    String url
    String callback

    static belongsTo = [ owner:User ]

    static hasMany = [
        tokens:AccessToken,
        codes:AuthorizationCode
    ]

    static constraints = {
        description nullable: true, blank:true
        url nullable: true, blank: true
    }

    static attrFetchModes = [
        'owner'  : 'join',
        'codes'  : 'eager',
        'tokens' : 'eager'
    ]

}
