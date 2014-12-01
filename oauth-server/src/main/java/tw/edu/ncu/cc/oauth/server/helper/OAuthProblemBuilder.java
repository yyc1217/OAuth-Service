package tw.edu.ncu.cc.oauth.server.helper;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;

public class OAuthProblemBuilder {

    private OAuthProblemException e;

    protected OAuthProblemBuilder( String error ) {
        e = OAuthProblemException.error( error );
    }

    public static OAuthProblemBuilder error( String error ) {
        return new OAuthProblemBuilder( error );
    }

    public OAuthProblemBuilder description( String description ) {
        e.description( description );
        return this;
    }

    public OAuthProblemBuilder redirectUri( String redirectUri ) {
        e.setRedirectUri( redirectUri );
        return this;
    }


    public OAuthProblemBuilder state( String state ) {
        e.state( state );
        return this;
    }

    public OAuthProblemException build() {
        return e;
    }

}
