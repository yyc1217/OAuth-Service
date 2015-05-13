package tw.edu.ncu.cc.oauth.server.concepts.user

import org.hibernate.annotations.Where
import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessToken
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.domain.BasicEntity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class User extends BasicEntity {

    @Column( unique = true, nullable = false )
    def String name

    @OneToMany( mappedBy = "owner", cascade = [ CascadeType.ALL ] )
    def Set< Client > clients = new HashSet<>()

    @OneToMany( mappedBy = "user", cascade = [ CascadeType.MERGE ] )
    @Where( clause = "date_expired > CURRENT_TIMESTAMP()" )
    def Set< AccessToken > accessTokens = new HashSet<>()

    @OneToMany( mappedBy = "user", cascade = [ CascadeType.MERGE ] )
    @Where( clause = "date_expired > CURRENT_TIMESTAMP()" )
    def Set< AuthorizationCode > codes = new HashSet<>()

}
