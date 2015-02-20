package tw.edu.ncu.cc.oauth.server.config;

public class OauthConfig {

    private long authCodeExpireSeconds;
    private long accessTokenExpireSeconds;

    public long getAuthCodeExpireSeconds() {
        return authCodeExpireSeconds;
    }

    public void setAuthCodeExpireSeconds( long authCodeExpireSeconds ) {
        this.authCodeExpireSeconds = authCodeExpireSeconds;
    }

    public long getAccessTokenExpireSeconds() {
        return accessTokenExpireSeconds;
    }

    public void setAccessTokenExpireSeconds( long accessTokenExpireSeconds ) {
        this.accessTokenExpireSeconds = accessTokenExpireSeconds;
    }

}
