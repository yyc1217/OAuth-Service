package tw.edu.ncu.cc.oauth.data.accesstoken;

import tw.edu.ncu.cc.oauth.data.Permission;

import java.util.Date;

public class UserAccessToken {

    private int id;
    private String clientName;
    private String clientUrl;
    private Date lastUpdate;
    private Permission[] scope;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName( String clientName ) {
        this.clientName = clientName;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl( String clientUrl ) {
        this.clientUrl = clientUrl;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate( Date lastUpdate ) {
        this.lastUpdate = lastUpdate;
    }

    public Permission[] getScope() {
        return scope;
    }

    public void setScope( Permission[] scope ) {
        this.scope = scope;
    }

}
