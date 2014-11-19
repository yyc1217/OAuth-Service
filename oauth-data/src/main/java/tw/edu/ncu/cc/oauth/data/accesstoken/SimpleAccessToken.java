package tw.edu.ncu.cc.oauth.data.accesstoken;

import tw.edu.ncu.cc.oauth.data.Permission;

public class SimpleAccessToken {

    private String token;
    private Permission[] scope;

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    public Permission[] getScope() {
        return scope;
    }

    public void setScope( Permission[] scope ) {
        this.scope = scope;
    }

}
