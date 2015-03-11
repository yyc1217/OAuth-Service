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

    def unexpiredCodes() {
        def q = AuthorizationCode.where {
            client == this && dateExpired > new Date()
        }
        return q.list()
    }

    def unexpiredTokens() {
        def q = AccessToken.where {
            client == this && dateExpired > new Date()
        }
        return q.list()
    }

    def revokeCodes() {
        executeUpdate(
                "UPDATE FROM AuthorizationCode SET dateExpired = :time WHERE client = :client", [
                        time: new Date(),
                        client: this
                ]
        )
    }

    def revokeTokens() {
        executeUpdate(
                "UPDATE FROM AccessToken SET dateExpired = :time WHERE client = :client",[
                        time: new Date(),
                        client: this
                ]
        )
    }

}
