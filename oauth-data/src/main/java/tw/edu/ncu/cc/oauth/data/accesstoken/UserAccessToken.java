package tw.edu.ncu.cc.oauth.data.accesstoken;


import java.util.Date;

public class UserAccessToken {

    private int id;
    private String clientName;
    private String clientUrl;
    private Date lastUpdate;
    private String[] scope;

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

    public String[] getScope() {
        return scope;
    }

    public void setScope( String[] scope ) {
        this.scope = scope;
    }

}
