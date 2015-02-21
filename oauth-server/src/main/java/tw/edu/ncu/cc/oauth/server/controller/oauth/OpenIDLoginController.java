package tw.edu.ncu.cc.oauth.server.controller.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tw.edu.ncu.cc.oauth.server.service.OpenIDService;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class OpenIDLoginController {

    private OpenIDService openIDService;

    @Autowired
    public void setOpenIDService( OpenIDService openIDService ) {
        this.openIDService = openIDService;
    }

    @RequestMapping( value = "login", method = RequestMethod.GET )
    public void login( HttpServletResponse response ) throws IOException {
        response.sendRedirect( openIDService.getLoginPath() );
    }

    @RequestMapping( value = "login_confirm", method = RequestMethod.GET )
    public void loginConfirm( HttpServletRequest request, HttpServletResponse response ) throws IOException {
        try {

            openIDService.login( request );

            if( getPreviousRequest( request ) != null ) {
                response.sendRedirect( getPreviousURL( request ) );
            } else {
                throw new LoginException( "no previous page represented" );
            }

        } catch ( LoginException e ) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST, e.getMessage() );
        }
    }

    private String getPreviousURL( HttpServletRequest request ) {
        return getPreviousRequest( request ).getRedirectUrl();
    }

    private SavedRequest getPreviousRequest( HttpServletRequest request ) {
        return ( SavedRequest ) request.getSession().getAttribute( "SPRING_SECURITY_SAVED_REQUEST" );
    }

}
