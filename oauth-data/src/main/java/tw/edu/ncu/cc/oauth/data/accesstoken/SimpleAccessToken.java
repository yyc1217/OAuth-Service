package tw.edu.ncu.cc.oauth.data.accesstoken;

public class SimpleAccessToken {

    private String token;
    private String[] scope;

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    public String[] getScope() {
        return scope;
    }

    public void setScope( String[] scope ) {
        this.scope = scope;
    }

}
