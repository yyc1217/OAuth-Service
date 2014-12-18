package tw.edu.ncu.cc.oauth.data.v1.management.token;

import tw.edu.ncu.cc.oauth.data.v1.management.application.IdApplication;

public class AppAccessToken extends AccessToken {

    private IdApplication application;

    public IdApplication getApplication() {
        return application;
    }

    public void setApplication( IdApplication application ) {
        this.application = application;
    }

}
