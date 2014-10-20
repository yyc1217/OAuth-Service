package tw.edu.ncu.cc.oauth.server.db.data;

import tw.edu.ncu.cc.oauth.server.db.data.base.BasicEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AccessTokenEntity extends BasicEntity {

    @Column( unique = true )
    private String token;

    @OneToOne( fetch = FetchType.LAZY )
    private ClientEntity client;

    @OneToOne( fetch = FetchType.LAZY )
    private UserEntity user;

    @OneToMany( fetch = FetchType.LAZY )
    private Set<PermissionEntity> scope;

    public AccessTokenEntity() { }

    public AccessTokenEntity( String token, ClientEntity client, UserEntity user, Set<PermissionEntity> scope ) {
        this.token = token;
        this.client = client;
        this.user = user;
        this.scope = scope;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser( UserEntity user ) {
        this.user = user;
    }
}
