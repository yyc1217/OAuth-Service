package tw.edu.ncu.cc.oauth.server.entity;

import tw.edu.ncu.cc.oauth.server.entity.base.TokenEntity;

import javax.persistence.*;

@Entity
@Table( name = "ACCESSTOKENENTITY" )
public class AccessTokenEntity extends TokenEntity {

    private String token;
    private ClientEntity client;
    private UserEntity user;

    @Basic
    @Column( name = "TOKEN", unique = true )
    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    @OneToOne
    @JoinColumn( name = "CLIENT_ID" )
    public ClientEntity getClient() {
        return client;
    }

    public void setClient( ClientEntity client ) {
        this.client = client;
    }

    @OneToOne
    @JoinColumn( name = "USER_ID" )
    public UserEntity getUser() {
        return user;
    }

    public void setUser( UserEntity user ) {
        this.user = user;
    }

}
