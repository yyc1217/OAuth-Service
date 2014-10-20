package tw.edu.ncu.cc.oauth.server.db.data;

import tw.edu.ncu.cc.oauth.server.db.data.base.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class UserEntity extends BasicEntity {

    @Column( unique = true )
    private String name;

    @OneToMany( fetch = FetchType.LAZY )
    private Set<AccessTokenEntity> tokens;

    @OneToMany( fetch = FetchType.LAZY )
    private Set<ClientEntity> clients;

    public UserEntity() { }

    public UserEntity( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName( String account ) {
        this.name = account;
    }

    public Set<AccessTokenEntity> getTokens() {
        return tokens;
    }

    public void setTokens( Set<AccessTokenEntity> tokens ) {
        this.tokens = tokens;
    }

    public Set<ClientEntity> getClients() {
        return clients;
    }

    public void setClients( Set<ClientEntity> clients ) {
        this.clients = clients;
    }
}
