package tw.edu.ncu.cc.oauth.server.data;

public class Secret {

    private int id;
    private String secret;

    public Secret( int id, String secret ) {
        this.id = id;
        this.secret = secret;
    }

    public int getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

}
