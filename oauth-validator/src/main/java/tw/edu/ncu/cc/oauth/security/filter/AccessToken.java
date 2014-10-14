package tw.edu.ncu.cc.oauth.security.filter;

import java.util.List;

class AccessToken {

    private List<String> scope;

    public List<String> getScope() {
        return scope;
    }

    public void setScope( List<String> scope ) {
        this.scope = scope;
    }

}
