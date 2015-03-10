package tw.edu.ncu.cc.oauth.data.v1.management.token;

import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClient;

public class ClientAccessToken extends AccessToken {

    private IdClient application;

    public IdClient getApplication() {
        return application;
    }

    public void setApplication( IdClient application ) {
        this.application = application;
    }

}
