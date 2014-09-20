package tw.edu.ncu.cc.oauth.factory;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.glassfish.hk2.api.Factory;

public class OAuthIssuerFactory implements Factory<OAuthIssuer> {

    @Override
    public OAuthIssuer provide() {
        return new OAuthIssuerImpl( new MD5Generator() );
    }

    @Override
    public void dispose( OAuthIssuer instance ) {

    }

}
