package tw.edu.ncu.cc.oauth.server.entity;

import tw.edu.ncu.cc.oauth.server.entity.base.BasicEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table( name = "USERENTITY" )
public class UserEntity extends BasicEntity {

    private String name;
    private Set<AccessTokenEntity> tokens;
    private Set<ClientEntity > clients;

    @Basic
    @Column( name = "NAME" )
    public String getName() {
        return name;
    }

    public void setName( String account ) {
        this.name = account;
    }

    @ManyToMany
    @JoinTable(
        name = "USERENTITY_ACCESSTOKENENTITY",
        joinColumns        = @JoinColumn( name = "USERENTITYS_ID" ),
        inverseJoinColumns = @JoinColumn( name = "TOKENS_ID" )
    )
    public Set<AccessTokenEntity> getTokens() {
        return tokens;
    }

    public void setTokens( Set<AccessTokenEntity> tokens ) {
        this.tokens = tokens;
    }

    @ManyToMany
    @JoinTable(
            name = "USERENTITY_CLIENTENTITY",
            joinColumns        = @JoinColumn( name = "USERENTITYS_ID" ),
            inverseJoinColumns = @JoinColumn( name = "CLIENTS_ID" )
    )
    public Set<ClientEntity > getClients() {
        return clients;
    }

    public void setClients( Set<ClientEntity > clients ) {
        this.clients = clients;
    }

}
