package tw.edu.ncu.cc.oauth.server.concepts.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.manage.openid.OpenIDManager
import tw.edu.ncu.cc.oauth.server.concepts.log.LogService
import tw.edu.ncu.cc.oauth.server.concepts.user.User
import tw.edu.ncu.cc.oauth.server.concepts.user.UserService

import javax.security.auth.login.LoginException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@Service
class OpenIdServiceImpl implements OpenIdService {

    @Autowired
    def LogService logService

    @Autowired
    def UserService userService;

    @Autowired
    def OpenIDManager openIDManager;

    @Override
    public String getLoginPath() {
        return openIDManager.getURLString();
    }

    @Override
    public void login( HttpServletRequest request ) throws LoginException {

        if( openIDManager.isValid( request ) ) {
            String name = openIDManager.getIdentity( request );

            User user = userService.findByName( name );
            if( user == null ) {
                user = userService.create( new User( name: name ) )
            }
            integrateWithSpringSecurity( request, user );
            logService.info( "LOGIN", "USER:" + name )
        } else {
            throw new LoginException( "openid response is incorrect" );
        }
    }

    private static void integrateWithSpringSecurity( HttpServletRequest request, User user ) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.name, "", AuthorityUtils.createAuthorityList( buildRoles( user ) )
        );

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication( authentication );

        HttpSession newSession = request.getSession( true );
        newSession.setAttribute( "SPRING_SECURITY_CONTEXT", securityContext );
        newSession.setMaxInactiveInterval( 5*60 );
    }

    private static String buildRoles( User user ) {
        user.roles.inject( "ROLE_USER" ) { roles, role ->
            roles + " " + role.name
        }
    }

}
