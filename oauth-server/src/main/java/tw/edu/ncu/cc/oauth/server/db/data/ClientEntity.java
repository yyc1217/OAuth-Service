package tw.edu.ncu.cc.oauth.server.db.data;

import tw.edu.ncu.cc.oauth.server.db.data.base.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ClientEntity extends BasicEntity {

    private String secret;

    @Column( unique = true )
    private String name;
    private String description;
    private String url;
    private String callback;

    public ClientEntity() { }

    public ClientEntity( String secret, String name, String callback ) {
        this.secret = secret;
        this.name = name;
        this.callback = callback;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret( String secret ) {
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback( String callback ) {
        this.callback = callback;
    }

}
