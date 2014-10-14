package tw.edu.ncu.cc.oauth.server.factory;

import org.glassfish.hk2.api.Factory;
import tw.edu.ncu.cc.oauth.server.view.AuthBean;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

public class AuthBeanFactory implements Factory<AuthBean> {

    private HttpSession session;

    @Inject
    public AuthBeanFactory( HttpSession session ) {
        this.session = session;
    }

    @Override
    public AuthBean provide() {
        return new AuthBean( session );
    }

    @Override
    public void dispose( AuthBean instance ) {

    }
}
