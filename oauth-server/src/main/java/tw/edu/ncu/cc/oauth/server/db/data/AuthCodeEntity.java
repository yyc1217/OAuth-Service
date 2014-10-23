package tw.edu.ncu.cc.oauth.server.db.data;

import tw.edu.ncu.cc.oauth.server.db.data.base.TokenEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class AuthCodeEntity extends TokenEntity {

    @Column( unique = true )
    private String code;

    @OneToOne( fetch = FetchType.LAZY )
    private UserEntity user;

    @OneToOne( fetch = FetchType.LAZY )
    private ClientEntity client;

    public AuthCodeEntity() { }

    public AuthCodeEntity( String code, UserEntity user, ClientEntity client ) {
        this.code = code;
        this.user = user;
        this.client = client;
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser( UserEntity user ) {
        this.user = user;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient( ClientEntity client ) {
        this.client = client;
    }

}
