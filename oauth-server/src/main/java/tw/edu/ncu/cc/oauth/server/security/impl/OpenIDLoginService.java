package tw.edu.ncu.cc.oauth.server.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tw.edu.ncu.cc.manage.openid.OpenIDException;
import tw.edu.ncu.cc.manage.openid.OpenIDManager;
import tw.edu.ncu.cc.oauth.server.security.LoginService;
import tw.edu.ncu.cc.oauth.server.service.UserService;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class OpenIDLoginService implements LoginService {

    private UserService userService;
    private OpenIDManager openIDManager;

    public OpenIDLoginService() {
        try {
            openIDManager = new OpenIDManager();
        } catch ( OpenIDException e ) {
            throw new RuntimeException( "cannot init open id manager", e );
        }
    }

    @Autowired
    public void setUserService( UserService userService ) {
        this.userService = userService;
    }

    @Override
    public String getLoginPath() {
        return openIDManager.getURLString();
    }

    @Override
    public void authenticate( HttpServletRequest request ) throws LoginException {

        if( openIDManager.isValid( request ) ) {
            String userName = openIDManager.getIdentity( request );
            userService.createUserIfNotExist( userName );
            integrateWithSpringSecurity( request, userName );
        } else {
            throw new LoginException( "openid response is incorrect" );
        }
    }

    private void integrateWithSpringSecurity( HttpServletRequest request, String userName ) {
        Authentication authentication = new UsernamePasswordAuthenticationToken( userName, "", AuthorityUtils.createAuthorityList( "ROLE_USER" ) );

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication( authentication );

        HttpSession newSession = request.getSession( true );
        newSession.setAttribute( "SPRING_SECURITY_CONTEXT", securityContext );
        newSession.setMaxInactiveInterval( 5*60 );
    }


}
