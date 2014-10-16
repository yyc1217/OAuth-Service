package tw.edu.ncu.cc.oauth.server.filter;

import tw.edu.ncu.cc.manage.openid.OpenIDManager;
import tw.edu.ncu.cc.oauth.server.factory.OpenIDFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogginFilter implements Filter {

    private OpenIDManager openIDManager;

    @Override
    public void init( FilterConfig filterConfig ) throws ServletException {
        openIDManager = new OpenIDFactory().provide();
    }

    @Override
    public void doFilter( ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain ) throws IOException, ServletException {
        HttpServletRequest  request  = ( HttpServletRequest ) servletRequest;
        HttpServletResponse response = ( HttpServletResponse ) servletResponse;
        HttpSession session = request.getSession();
        if( session.getAttribute( "portalID" ) == null && request.getQueryString() != null ) {
            session.setAttribute( "queryString", request.getQueryString() );
            response.sendRedirect( openIDManager.getURLString() );
        } else {
            chain.doFilter( servletRequest, servletResponse );
        }
    }

    @Override
    public void destroy() {

    }

}
