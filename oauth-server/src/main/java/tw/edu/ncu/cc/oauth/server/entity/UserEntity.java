package tw.edu.ncu.cc.oauth.server.entity;

import org.hibernate.annotations.Where;
import tw.edu.ncu.cc.oauth.server.entity.base.BasicEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "user" )
public class UserEntity extends BasicEntity {

    private String name;
    private Set<ClientEntity> clients = new HashSet<>();
    private Set<AccessTokenEntity> tokens = new HashSet<>();

    @Basic
    @Column( name = "name", unique = true )
    public String getName() {
        return name;
    }

    public void setName( String account ) {
        this.name = account;
    }

    @OneToMany( mappedBy = "owner")
    public Set<ClientEntity > getClients() {
        return clients;
    }

    public void setClients( Set<ClientEntity > clients ) {
        this.clients = clients;
    }

    @OneToMany( mappedBy = "user" )
    @Where( clause = "date_expired > CURRENT_TIMESTAMP() or date_expired IS NULL" )
    public Set<AccessTokenEntity> getTokens() {
        return tokens;
    }

    public void setTokens( Set<AccessTokenEntity> tokens ) {
        this.tokens = tokens;
    }

}
