package tw.edu.ncu.cc.oauth.server.entity;

import tw.edu.ncu.cc.oauth.server.entity.base.BasicEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "CLIENTENTITY" )
public class ClientEntity extends BasicEntity {

    private String secret;
    private String name;
    private String description;
    private String url;
    private String callback;

    @Basic
    @Column( name = "SECRET" )
    public String getSecret() {
        return secret;
    }

    public void setSecret( String secret ) {
        this.secret = secret;
    }

    @Basic
    @Column( name = "NAME" )
    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Basic
    @Column( name = "DESCRIPTION" )
    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    @Basic
    @Column( name = "URL" )
    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    @Basic
    @Column( name = "CALLBACK" )
    public String getCallback() {
        return callback;
    }

    public void setCallback( String callback ) {
        this.callback = callback;
    }

}
