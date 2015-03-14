package tw.edu.ncu.cc.oauth.server.helper.data;

public class SerialSecret {

    private long id;
    private String secret;

    public SerialSecret( long id, String secret ) {
        this.id = id;
        this.secret = secret;
    }

    public long getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

}
