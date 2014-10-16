package tw.edu.ncu.cc.oauth.server.factory;

import org.glassfish.hk2.api.Factory;
import tw.edu.ncu.cc.manage.openid.OpenIDException;
import tw.edu.ncu.cc.manage.openid.OpenIDManager;

public class OpenIDFactory implements Factory<OpenIDManager> {

    @Override
    public OpenIDManager provide() {
        try {
            return new OpenIDManager();
        } catch ( OpenIDException e ) {
            throw new RuntimeException( "cannot init openid manager", e );
        }
    }

    @Override
    public void dispose( OpenIDManager instance ) {

    }

}
