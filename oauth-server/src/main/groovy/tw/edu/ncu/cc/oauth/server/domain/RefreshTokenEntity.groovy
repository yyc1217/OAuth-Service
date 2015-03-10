package tw.edu.ncu.cc.oauth.server.domain

import tw.edu.ncu.cc.oauth.server.domain.base.TokenEntity

import javax.persistence.*

@Entity
@Table( name = "refresh_token" )
public class RefreshTokenEntity extends TokenEntity {

    private String token;
    private AccessTokenEntity accessToken;

    @Basic
    @Column( name = "token", unique = true )
    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    @OneToOne( optional = false )
    @JoinColumn( name = "access_token_id" )
    public AccessTokenEntity getAccessToken() {
        return accessToken;
    }

    public void setAccessToken( AccessTokenEntity accessToken ) {
        this.accessToken = accessToken;
    }

}
