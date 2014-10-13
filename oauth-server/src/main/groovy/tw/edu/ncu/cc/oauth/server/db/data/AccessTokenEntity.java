package tw.edu.ncu.cc.oauth.server.db.data;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AccessTokenEntity {

    @Id @GeneratedValue
    private Integer id;

    @Column( unique = true )
    private String token;

    @OneToOne( fetch = FetchType.LAZY )
    private ClientEntity client;

    @OneToMany( fetch = FetchType.LAZY )
    private Set<PermissionEntity> scope;

    public AccessTokenEntity() { }

    public AccessTokenEntity( String token, ClientEntity client, Set<PermissionEntity> scope ) {
        this.token = token;
        this.client = client;
        this.scope = scope;
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient( ClientEntity client ) {
        this.client = client;
    }

    public Set<PermissionEntity> getScope() {
        return scope;
    }

    public void setScope( Set<PermissionEntity> scope ) {
        this.scope = scope;
    }

}
