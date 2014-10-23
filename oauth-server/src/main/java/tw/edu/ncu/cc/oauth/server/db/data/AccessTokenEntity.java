package tw.edu.ncu.cc.oauth.server.db.data;

import tw.edu.ncu.cc.oauth.server.db.data.base.TokenEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class AccessTokenEntity extends TokenEntity {

    @Column( unique = true )
    private String token;

    @OneToOne( fetch = FetchType.LAZY )
    private ClientEntity client;

    @OneToOne( fetch = FetchType.LAZY )
    private UserEntity user;

    public AccessTokenEntity() { }

    public AccessTokenEntity( String token, ClientEntity client, UserEntity user ) {
        this.token = token;
        this.client = client;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient( ClientEntity client ) {
        this.client = client;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser( UserEntity user ) {
        this.user = user;
    }
}
