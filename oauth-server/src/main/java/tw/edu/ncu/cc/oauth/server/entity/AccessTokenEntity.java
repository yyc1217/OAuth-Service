package tw.edu.ncu.cc.oauth.server.entity;

import tw.edu.ncu.cc.oauth.server.entity.base.TokenEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "access_token" )
public class AccessTokenEntity extends TokenEntity {

    private String token;

    @Basic
    @Column( name = "token", unique = true )
    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

}
