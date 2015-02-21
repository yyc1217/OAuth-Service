package tw.edu.ncu.cc.oauth.server.helper;

public class OAuthURLBuilder {

    private String code;
    private String state;
    private String error;
    private String callback;
    private String errorDescription;

    private OAuthURLBuilder( String callback ) {
        this.callback = callback;
    }

    public static OAuthURLBuilder url( String url ) {
        return new OAuthURLBuilder( url );
    }

    public OAuthURLBuilder state( String state ) {
        this.state = state;
        return this;
    }

    public OAuthURLBuilder code( String code ) {
        this.code = code;
        return this;
    }

    public OAuthURLBuilder error( String error ) {
        this.error = error;
        return this;
    }

    public OAuthURLBuilder errorDescription( String errorDescription ) {
        this.errorDescription = errorDescription;
        return this;
    }

    public String build() {
        StringBuilder stringBuilder = new StringBuilder( callback + "?" );
        if( isNotEmpty( state ) ) {
            stringBuilder.append( "state=" ).append( state ).append( "&" );
        }
        if( isNotEmpty( error ) ) {
            stringBuilder.append( "error=" ).append( error ).append( "&" );
            if( isNotEmpty( errorDescription ) ) {
                stringBuilder.append( "error_description=" ).append( errorDescription ).append( "&" );
            }
        } else {
            if( isNotEmpty( code ) ) {
                stringBuilder.append( "code=" ).append( code ).append( "&" );
            }
        }
        return stringBuilder.deleteCharAt( stringBuilder.length()-1 ).toString();
    }

    private boolean isNotEmpty( String string ) {
        return string != null && !string.equals( "" );
    }

}
