package tw.edu.ncu.cc.security.data;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Column( unique = true )
    private String  account;

    @OneToMany
    private Set<AccessToken> tokens;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount( String account ) {
        this.account = account;
    }

    public Set<AccessToken> getTokens() {
        return tokens;
    }

    public void setTokens( Set<AccessToken> tokens ) {
        this.tokens = tokens;
    }

}
