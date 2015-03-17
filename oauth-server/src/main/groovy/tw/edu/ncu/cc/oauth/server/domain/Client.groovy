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

    static belongsTo = [ owner:User ]

    static hasMany = [
        tokens:AccessToken,
        codes:AuthorizationCode
    ]

    static constraints = {
        description nullable: true, blank:true
        url nullable: true, blank: true
    }

    static lazyAttrModes = [
        'owner'  : 'join',
        'codes'  : 'eager',
        'tokens' : 'eager'
    ]

}
