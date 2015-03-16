package tw.edu.ncu.cc.oauth.server.web.oauth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.savedrequest.SavedRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tw.edu.ncu.cc.oauth.server.service.security.OpenIdService

import javax.security.auth.login.LoginException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
public class OpenIdLoginController {

    @Autowired
    def OpenIdService openIdService;

    @RequestMapping( value = "login_page", method = RequestMethod.GET )
    public void login( HttpServletResponse response ) throws IOException {
        response.sendRedirect( openIdService.getLoginPath() );
    }

    @RequestMapping( value = "login_confirm", method = RequestMethod.GET )
    public void loginConfirm( HttpServletRequest request, HttpServletResponse response ) throws IOException {
        try {

            openIdService.login( request );

            if( getPreviousRequest( request ) != null ) {
                response.sendRedirect( getPreviousURL( request ) );
            } else {
                throw new LoginException( "no previous page represented" );
            }

        } catch ( LoginException e ) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST, e.getMessage() );
        }
    }

    private static String getPreviousURL( HttpServletRequest request ) {
        return getPreviousRequest( request ).getRedirectUrl();
    }

    private static SavedRequest getPreviousRequest( HttpServletRequest request ) {
        return ( SavedRequest ) request.getSession().getAttribute( "SPRING_SECURITY_SAVED_REQUEST" );
    }

}