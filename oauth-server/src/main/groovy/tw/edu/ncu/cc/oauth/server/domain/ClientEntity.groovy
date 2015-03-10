package tw.edu.ncu.cc.oauth.server.domain

import org.hibernate.annotations.Where
import tw.edu.ncu.cc.oauth.server.domain.base.BasicEntity

import javax.persistence.*

@Entity
@Table( name = "client" )
public class ClientEntity extends BasicEntity {

    private String secret;
    private String name;
    private String description;
    private String url;
    private String callback;
    private UserEntity owner;
    private Set<AccessTokenEntity> tokens = new HashSet<>();

    @Basic
    @Column( name = "secret" )
    public String getSecret() {
        return secret;
    }

    public void setSecret( String secret ) {
        this.secret = secret;
    }

    @Basic
    @Column( name = "name" )
    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Basic
    @Column( name = "description" )
    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    @Basic
    @Column( name = "url" )
    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    @Basic
    @Column( name = "callback" )
    public String getCallback() {
        return callback;
    }

    public void setCallback( String callback ) {
        this.callback = callback;
    }

    @ManyToOne( optional = false )
    @JoinColumn( name = "owner_id" )
    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner( UserEntity user ) {
        this.owner = user;
    }

    @OneToMany( mappedBy = "client" )
    @Where( clause = "date_expired > CURRENT_TIMESTAMP() or date_expired IS NULL" )
    public Set< AccessTokenEntity > getTokens() {
        return tokens;
    }

    public void setTokens( Set< AccessTokenEntity > tokens ) {
        this.tokens = tokens;
    }

}
