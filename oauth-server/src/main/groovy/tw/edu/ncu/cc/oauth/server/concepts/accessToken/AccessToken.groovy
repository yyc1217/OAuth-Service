package tw.edu.ncu.cc.oauth.server.concepts.accessToken

import tw.edu.ncu.cc.oauth.server.concepts.permission.Permission
import tw.edu.ncu.cc.oauth.server.domain.TokenEntity

import javax.persistence.*

@Entity
public class AccessToken extends TokenEntity {

    @Transient
    def String token

    @Column( unique = true, nullable = false )
    def String encryptedToken

    @ManyToMany
    @JoinTable(
        name = "access_token_scope",
        joinColumns = @JoinColumn( name = "access_token_id" ),
        inverseJoinColumns = @JoinColumn( name = "permission_id" )
    )
    def Set< Permission > scope

}
