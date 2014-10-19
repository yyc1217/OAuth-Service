package tw.edu.ncu.cc.oauth.server.filter;

import tw.edu.ncu.cc.manage.openid.OpenIDManager;
import tw.edu.ncu.cc.oauth.server.factory.OpenIDFactory;
import tw.edu.ncu.cc.oauth.server.rule.LoginRule;

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
    public void destroy() {

    }

    @Override
    public void doFilter( ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain ) throws IOException, ServletException {

        HttpServletRequest  request  = ( HttpServletRequest ) servletRequest;
        HttpServletResponse response = ( HttpServletResponse ) servletResponse;
        HttpSession session = request.getSession();

        if( isUserLogin( session )  ) {
            chain.doFilter( servletRequest, servletResponse );
        } else {
            saveQueryString( session, request.getQueryString() );
            redirectToLoginPage( response );
        }
    }

    private static boolean isUserLogin( HttpSession session ) {
        return session.getAttribute( LoginRule.KEY_LOGIN_ID ) != null;
    }

    private static void saveQueryString( HttpSession session, String queryString ) {
        session.setAttribute( LoginRule.KEY_QUERY_STRING, queryString );
    }

    private void redirectToLoginPage( HttpServletResponse response ) throws IOException {
        response.sendRedirect( openIDManager.getURLString() );
    }

}
