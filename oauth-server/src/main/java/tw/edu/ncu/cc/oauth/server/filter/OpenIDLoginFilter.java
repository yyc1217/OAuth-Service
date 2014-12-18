package tw.edu.ncu.cc.oauth.server.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import tw.edu.ncu.cc.oauth.server.security.LoginService;

import javax.security.auth.login.LoginException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OpenIDLoginFilter extends AbstractFilter {

    private String loginPath = "/login";
    private String loginConfirmPath = "/login_confirm";
    private LoginService loginService;

    public void setLoginPath( String loginPage ) {
        this.loginPath = loginPage;
    }

    public void setLoginConfirmPath( String loginConfirmPage ) {
        this.loginConfirmPath = loginConfirmPage;
    }

    @Autowired
    public void setLoginService( LoginService loginService ) {
        this.loginService = loginService;
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {

        HttpServletRequest  httpRequest  = ( HttpServletRequest ) request;
        HttpServletResponse httpResponse = ( HttpServletResponse ) response;
        String requestPath = httpRequest.getRequestURI().substring( httpRequest.getContextPath().length() );

        if( requestPath.equals( loginPath ) ) {
            httpResponse.sendRedirect( loginService.getLoginPath() );
        } else if( requestPath.equals( loginConfirmPath ) ) {
            try {

                loginService.authenticate( httpRequest );

                if( getPreviousRequest( httpRequest ) != null ) {
                    httpResponse.sendRedirect( getPreviousURL( httpRequest ) );
                } else {
                    throw new LoginException( "cannot redirect to previous page" );
                }

            } catch ( LoginException e ) {
                httpResponse.sendError( HttpServletResponse.SC_BAD_REQUEST, e.getMessage() );
            }
        } else {
            chain.doFilter( request, response );
        }
    }

    public String getPreviousURL( HttpServletRequest request ) {
        return getPreviousRequest( request ).getRedirectUrl();
    }

    private SavedRequest getPreviousRequest( HttpServletRequest request ) {
        return ( SavedRequest ) request.getSession().getAttribute( "SPRING_SECURITY_SAVED_REQUEST" );
    }

}
