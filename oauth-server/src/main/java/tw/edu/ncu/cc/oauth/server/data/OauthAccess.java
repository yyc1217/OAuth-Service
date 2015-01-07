package tw.edu.ncu.cc.oauth.server.data;

import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;

import java.util.Set;


public class OauthAccess {

    private String state;
    private String userID;
    private ClientEntity client;
    private Set<String> scope;

    public Set< String > getScope() {
        return scope;
    }

    public void setScope( Set< String > scope ) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState( String state ) {
        this.state = state;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID( String userID ) {
        this.userID = userID;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient( ClientEntity client ) {
        this.client = client;
    }

}
