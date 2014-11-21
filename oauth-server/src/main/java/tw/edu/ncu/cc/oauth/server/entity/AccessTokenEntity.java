package tw.edu.ncu.cc.oauth.server.entity;

import tw.edu.ncu.cc.oauth.server.entity.base.TokenEntity;

import javax.persistence.*;

@Entity
@Table( name = "access_token" )
public class AccessTokenEntity extends TokenEntity {

    private String token;
    private UserEntity user;
    private ClientEntity client;

    @Basic
    @Column( name = "token", unique = true )
    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    @ManyToOne( optional = false )
    @JoinColumn( name = "user_id" )
    public UserEntity getUser() {
        return user;
    }

    public void setUser( UserEntity user ) {
        this.user = user;
    }

    @ManyToOne( optional = false )
    @JoinColumn( name = "client_id" )
    public ClientEntity getClient() {
        return client;
    }

    public void setClient( ClientEntity client ) {
        this.client = client;
    }

}
