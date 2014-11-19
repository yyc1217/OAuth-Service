package tw.edu.ncu.cc.oauth.server.entity;

import tw.edu.ncu.cc.oauth.server.entity.base.BasicEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class UserEntity extends BasicEntity {

    private String name;
    private Set<AccessTokenEntity> tokens;
    private Set<ApplicationEntity> clients;

    @Basic
    @Column( unique = true )
    public String getName() {
        return name;
    }

    public void setName( String account ) {
        this.name = account;
    }

    @OneToMany( fetch = FetchType.LAZY )
    public Set<AccessTokenEntity> getTokens() {
        return tokens;
    }

    public void setTokens( Set<AccessTokenEntity> tokens ) {
        this.tokens = tokens;
    }

    @OneToMany( fetch = FetchType.LAZY )
    public Set<ApplicationEntity> getClients() {
        return clients;
    }

    public void setClients( Set<ApplicationEntity> clients ) {
        this.clients = clients;
    }

}
