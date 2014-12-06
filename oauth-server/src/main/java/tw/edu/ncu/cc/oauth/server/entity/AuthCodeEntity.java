package tw.edu.ncu.cc.oauth.server.entity;

import tw.edu.ncu.cc.oauth.server.entity.base.TokenEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "auth_code" )
public class AuthCodeEntity extends TokenEntity {

    private String code;

    @Basic
    @Column( name = "code", unique = true )
    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

}
