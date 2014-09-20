package tw.edu.ncu.cc.oauth.security.filter;

import java.util.List;

class AccessToken {

    private List<String> scopes;

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes( List<String> scopes ) {
        this.scopes = scopes;
    }

}
