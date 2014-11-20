package tw.edu.ncu.cc.oauth.server.entity;

import tw.edu.ncu.cc.oauth.server.entity.base.TokenEntity;

import javax.persistence.*;

@Entity
@Table( name = "AUTHCODEENTITY" )
public class AuthCodeEntity extends TokenEntity {

    private String code;
    private UserEntity user;
    private ClientEntity client;

    @Basic
    @Column( name = "CODE" )
    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    @OneToOne
    @JoinColumn( name = "USER_ID" )
    public UserEntity getUser() {
        return user;
    }

    public void setUser( UserEntity user ) {
        this.user = user;
    }

    @OneToOne
    @JoinColumn( name = "CLIENT_ID" )
    public ClientEntity getClient() {
        return client;
    }

    public void setClient( ClientEntity client ) {
        this.client = client;
    }

}
