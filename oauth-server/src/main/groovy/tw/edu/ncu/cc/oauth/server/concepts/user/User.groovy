package tw.edu.ncu.cc.oauth.server.concepts.user

import org.hibernate.annotations.Where
import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessToken
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.concepts.role.Role
import tw.edu.ncu.cc.oauth.server.domain.BasicEntity

import javax.persistence.*

@Entity
class User extends BasicEntity {

    @Column( unique = true, nullable = false )
    def String name

    @OneToMany( mappedBy = "owner", cascade = [ CascadeType.ALL ] )
    @Where( clause = "deleted = 'false'" )
    def Set< Client > clients = new HashSet<>()

    @OneToMany( mappedBy = "user", cascade = [ CascadeType.MERGE ] )
    @Where( clause = "date_expired > CURRENT_TIMESTAMP()" )
    def Set< AccessToken > accessTokens = new HashSet<>()

    @OneToMany( mappedBy = "user", cascade = [ CascadeType.MERGE ] )
    @Where( clause = "date_expired > CURRENT_TIMESTAMP()" )
    def Set< AuthorizationCode > codes = new HashSet<>()

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn( name = "user_id" ),
            inverseJoinColumns = @JoinColumn( name = "role_id" )
    )
    def Set< Role > roles = new HashSet<>()

}
