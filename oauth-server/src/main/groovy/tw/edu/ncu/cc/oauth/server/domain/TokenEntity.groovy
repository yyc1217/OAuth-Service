package tw.edu.ncu.cc.oauth.server.domain

import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.concepts.user.User

import javax.persistence.*

@MappedSuperclass
class TokenEntity extends BasicEntity {

    @JoinColumn
    @ManyToOne( optional = false )
    def User user

    @JoinColumn
    @ManyToOne( optional = false )
    def Client client

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date dateExpired

    def revoke() {
        dateExpired = new Date( System.currentTimeMillis() )
    }

    def isExpired() {
        dateExpired < new Date()
    }

}
