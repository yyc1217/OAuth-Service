package tw.edu.ncu.cc.oauth.server.entity;

import tw.edu.ncu.cc.oauth.server.entity.base.TokenEntity;

import javax.persistence.*;

@Entity
public class AuthCodeEntity extends TokenEntity {

    private String code;
    private UserEntity user;
    private ApplicationEntity client;

    @Basic
    @Column( unique = true )
    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    @OneToOne( fetch = FetchType.LAZY )
    public UserEntity getUser() {
        return user;
    }

    public void setUser( UserEntity user ) {
        this.user = user;
    }

    @OneToOne( fetch = FetchType.LAZY )
    public ApplicationEntity getClient() {
        return client;
    }

    public void setClient( ApplicationEntity client ) {
        this.client = client;
    }

}
