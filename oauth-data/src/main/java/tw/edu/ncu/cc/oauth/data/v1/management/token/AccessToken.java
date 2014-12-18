package tw.edu.ncu.cc.oauth.data.v1.management.token;

import java.util.Date;

public class AccessToken {

    private String id;
    private String user;
    private String[] scope;
    private Date last_updated;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser( String user ) {
        this.user = user;
    }

    public String[] getScope() {
        return scope;
    }

    public void setScope( String[] scope ) {
        this.scope = scope;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated( Date last_updated ) {
        this.last_updated = last_updated;
    }

}
