package tw.edu.ncu.cc.oauth.server.entity;

import tw.edu.ncu.cc.oauth.server.entity.base.TokenEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

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

    @OneToOne
    public ApplicationEntity getClient() {
        return client;
    }

    public void setClient( ApplicationEntity client ) {
        this.client = client;
    }

    @OneToOne
    public UserEntity getUser() {
        return user;
    }

    public void setUser( UserEntity user ) {
        this.user = user;
    }

}
