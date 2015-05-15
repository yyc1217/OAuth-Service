package tw.edu.ncu.cc.oauth.server.helper.data;

public class SerialSecret {

    private Integer id;
    private String secret;

    public SerialSecret( Integer id, String secret ) {
        this.id = id;
        this.secret = secret;
    }

    public Integer getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

}
