package tw.edu.ncu.cc.oauth.server.helper;

public class OAuthRedirectBuilder {

    private String code;
    private String state;
    private String error;
    private String callback;

    private OAuthRedirectBuilder( String callback ) {
        this.callback = callback;
    }

    public static OAuthRedirectBuilder callback( String url ) {
        return new OAuthRedirectBuilder( url );
    }

    public OAuthRedirectBuilder state( String state ) {
        this.state = state;
        return this;
    }

    public OAuthRedirectBuilder code( String code ) {
        this.code = code;
        return this;
    }

    public OAuthRedirectBuilder error( String error ) {
        this.error = error;
        return this;
    }

    public String build() {
        StringBuilder stringBuilder = new StringBuilder( callback );
        if( state != null ) {
            stringBuilder.append( "?" ).append( "state=" ).append( state );
        }
        if( error == null ) {
            stringBuilder.append( "&" ).append( "code=" ).append( code );
        } else {
            stringBuilder.append( "&" ).append( "error=" ).append( error );
        }
        return stringBuilder.toString();
    }

}
