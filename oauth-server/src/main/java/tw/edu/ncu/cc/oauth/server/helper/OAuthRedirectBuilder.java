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
        StringBuilder stringBuilder = new StringBuilder( callback + "?" );
        if( state != null ) {
            stringBuilder.append( "state=" ).append( state ).append( "&" );
        }
        if( error == null ) {
            stringBuilder.append( "code=" ).append( code ).append( "&" );
        } else {
            stringBuilder.append( "error=" ).append( error ).append( "&" );
        }
        return stringBuilder.deleteCharAt( stringBuilder.length()-1 ).toString();
    }

}
