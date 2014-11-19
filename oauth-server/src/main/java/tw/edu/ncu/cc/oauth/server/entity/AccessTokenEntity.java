package tw.edu.ncu.cc.oauth.server.entity;

import tw.edu.ncu.cc.oauth.server.entity.base.TokenEntity;

import javax.persistence.*;

@Entity
public class AccessTokenEntity extends TokenEntity {

    private String token;
    private ApplicationEntity client;
    private UserEntity user;

    @Basic
    @Column( unique = true )
    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    @OneToOne( fetch = FetchType.LAZY )
    public ApplicationEntity getClient() {
        return client;
    }

    public void setClient( ApplicationEntity client ) {
        this.client = client;
    }

    @OneToOne( fetch = FetchType.LAZY )
    public UserEntity getUser() {
        return user;
    }

    public void setUser( UserEntity user ) {
        this.user = user;
    }

}
