package tw.edu.ncu.cc.oauth.server.factory;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.ValueGenerator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.glassfish.hk2.api.Factory;
import org.hibernate.Session;
import tw.edu.ncu.cc.oauth.server.db.model.impl.AccessTokenModelImpl;
import tw.edu.ncu.cc.oauth.server.db.model.impl.AuthCodemModelImpl;

import javax.inject.Inject;

public class OAuthIssuerFactory implements Factory<OAuthIssuer> {

    private class TokenIssuer implements OAuthIssuer {

        private ValueGenerator generator;
        private AuthCodemModelImpl authCodeModel;
        private AccessTokenModelImpl accessTokenModel;

        private TokenIssuer() {
            generator = new MD5Generator();
            authCodeModel    = new AuthCodemModelImpl();
            accessTokenModel = new AccessTokenModelImpl();
        }

        public void setSession( Session session ) {
            authCodeModel.setSession( session );
            accessTokenModel.setSession( session );
        }

        @Override
        public String accessToken() throws OAuthSystemException {
            while( true ) {
                String token = generator.generateValue();
                if( accessTokenModel.getAccessToken( token ) == null ) {
                    return token;
                }
            }
        }

        @Override
        public String authorizationCode() throws OAuthSystemException {
            while( true ) {
                String code = generator.generateValue();
                if( authCodeModel.getAuthCode( code ) == null ) {
                    return code;
                }
            }
        }

        @Override
        public String refreshToken() throws OAuthSystemException {
            return generator.generateValue();
        }

    }

    private static TokenIssuer issuer;

    private Session session;

    @Inject
    public OAuthIssuerFactory( Session session ) {
        this.session = session;
    }

    @Override
    public OAuthIssuer provide() {

        if ( issuer == null ) {
            issuer = new TokenIssuer();
        }

        issuer.setSession( session );

        return issuer;
    }

    @Override
    public void dispose( OAuthIssuer instance ) {

    }

}
