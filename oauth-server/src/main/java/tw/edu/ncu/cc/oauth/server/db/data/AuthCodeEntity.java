package tw.edu.ncu.cc.oauth.server.db.data;

import tw.edu.ncu.cc.oauth.server.db.data.base.BasicEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AuthCodeEntity extends BasicEntity {

    @Column( unique = true )
    private String code;

    @OneToOne( fetch = FetchType.LAZY )
    private UserEntity user;

    @OneToOne( fetch = FetchType.LAZY )
    private ClientEntity client;

    @OneToMany( fetch = FetchType.LAZY )
    private Set<PermissionEntity> scope;

    public AuthCodeEntity() { }

    public AuthCodeEntity( String code, UserEntity user, ClientEntity client, Set<PermissionEntity> scope ) {
        this.code = code;
        this.user = user;
        this.client = client;
        this.scope  = scope;
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser( UserEntity user ) {
        this.user = user;
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
