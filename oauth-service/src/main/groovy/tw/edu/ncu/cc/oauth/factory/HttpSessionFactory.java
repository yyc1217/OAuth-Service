package tw.edu.ncu.cc.oauth.factory;

import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpSessionFactory implements Factory<HttpSession> {

    private HttpServletRequest request;

    @Inject
    public HttpSessionFactory( HttpServletRequest request ) {
        this.request = request;
    }

    @Override
    public HttpSession provide() {
        return request.getSession();
    }

    @Override
    public void dispose( HttpSession instance ) {

    }

}
