package tw.edu.ncu.cc.oauth.db.data;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Column( unique = true )
    private String name;

    @OneToMany( fetch = FetchType.LAZY )
    private Set<AccessToken> tokens;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String account ) {
        this.name = account;
    }

    public Set<AccessToken> getTokens() {
        return tokens;
    }

    public void setTokens( Set<AccessToken> tokens ) {
        this.tokens = tokens;
    }

}
