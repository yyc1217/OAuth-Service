package tw.edu.ncu.cc.oauth.server.entity;

import tw.edu.ncu.cc.oauth.server.entity.base.PermissionEntity;

import javax.persistence.*;

@Entity
@Table( name = "auth_code" )
public class AuthCodeEntity extends PermissionEntity {

    private String code;
    private UserEntity user;
    private ClientEntity client;

    @Basic
    @Column( name = "code", unique = true )
    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
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
