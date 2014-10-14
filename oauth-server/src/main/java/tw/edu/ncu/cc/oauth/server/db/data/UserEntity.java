package tw.edu.ncu.cc.oauth.server.db.data;

import javax.persistence.*;
import java.util.Set;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column( unique = true )
    private String name;

    @OneToMany( fetch = FetchType.LAZY )
    private Set<AccessTokenEntity> tokens;

    public UserEntity() { }

    public UserEntity( String name ) {
        this.name = name;
    }

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

    public Set<AccessTokenEntity> getTokens() {
        return tokens;
    }

    public void setTokens( Set<AccessTokenEntity> tokens ) {
        this.tokens = tokens;
    }

}
