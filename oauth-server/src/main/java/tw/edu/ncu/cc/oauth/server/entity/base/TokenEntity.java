package tw.edu.ncu.cc.oauth.server.entity.base;

import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.entity.UserEntity;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class TokenEntity extends BasicEntity {

    private String scope;
    private UserEntity user;
    private ClientEntity client;
    private Date dateExpired;

    @Basic
    @Column( name = "scope" , nullable = false )
    public String getScope() {
        return scope;
    }

    public void setScope( String permission ) {
        this.scope = permission;
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

    @Column( name = "date_expired" )
    @Temporal( value = TemporalType.TIMESTAMP )
    public Date getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired( Date expireDate ) {
        this.dateExpired = expireDate;
    }

}
