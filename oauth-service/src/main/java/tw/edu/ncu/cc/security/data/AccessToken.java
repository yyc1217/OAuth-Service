package tw.edu.ncu.cc.security.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class AccessToken {

    @Id @GeneratedValue
    private Integer id;
    private String token;
    private Integer clientId;

    @OneToMany
    private Set<Permission> scope;

    public Set<Permission> getScope() {
        return scope;
    }

    public void setScope( Set<Permission> scope ) {
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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId( Integer clientId ) {
        this.clientId = clientId;
    }

}
