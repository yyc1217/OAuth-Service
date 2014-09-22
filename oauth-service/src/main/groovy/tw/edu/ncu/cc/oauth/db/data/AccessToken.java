package tw.edu.ncu.cc.oauth.db.data;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AccessToken {

    @Id @GeneratedValue
    private Integer id;

    @Column( unique = true )
    private String token;

    @OneToOne( fetch = FetchType.LAZY )
    private Client client;

    @OneToMany( fetch = FetchType.LAZY )
    private Set<Permission> scope;

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

    public Client getClient() {
        return client;
    }

    public void setClient( Client client ) {
        this.client = client;
    }

    public Set<Permission> getScope() {
        return scope;
    }

    public void setScope( Set<Permission> scope ) {
        this.scope = scope;
    }

}
