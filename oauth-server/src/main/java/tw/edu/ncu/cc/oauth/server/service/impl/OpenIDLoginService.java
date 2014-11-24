package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;
import tw.edu.ncu.cc.oauth.server.entity.UserEntity;
import tw.edu.ncu.cc.oauth.server.service.LoginService;
import tw.edu.ncu.cc.oauth.server.service.UserService;
import tw.edu.ncu.cc.openid.consumer.ncu.NCUOpenIDHandler;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
public class OpenIDLoginService implements LoginService {

    private NCUOpenIDHandler openIDHandler;
    private UserService userService;

    @Autowired
    public void setOpenIDHandler( NCUOpenIDHandler openIDHandler ) {
        this.openIDHandler = openIDHandler;
    }

    @Autowired
    public void setUserService( UserService userService ) {
        this.userService = userService;
    }

    @Override
    public String getLoginPath() {
        return openIDHandler.getAuthenticationURLString();
    }

    @Override
    public String getPreviousPage( HttpServletRequest request ) {
        HttpSession session = request.getSession();
        SavedRequest savedRequest = getSavedRequest( session );
        return savedRequest.getRedirectUrl();
    }

    @Override
    public void authenticate( HttpServletRequest request ) throws LoginException {
        HttpSession session  = request.getSession();
        Map< String, ? > map = request.getParameterMap();
        SavedRequest savedRequest = getSavedRequest( session );

        if( hasPreviousPage( savedRequest ) ) {
            if( isOpenIDResponseCorrect( map ) ) {
                String studentID = openIDHandler.getNCUConsumer( map ).getStudentID();
                if( isUserNotExist( studentID ) ) {
                    createNewUser ( studentID );
                }
                integrateWithSpringSecurity( request, studentID );
            } else {
                throw new LoginException( "openid response is incorrect" );
            }
        } else {
            throw new LoginException( "cannot redirect to previous page" );
        }
    }

    private SavedRequest getSavedRequest( HttpSession session ) {
        return ( SavedRequest ) session.getAttribute( "SPRING_SECURITY_SAVED_REQUEST" );
    }

    private boolean hasPreviousPage( SavedRequest savedRequest ) {
        return savedRequest != null;
    }

    private boolean isOpenIDResponseCorrect( Map< String, ? > map ) {
        return openIDHandler.isResponseMapValid( map );
    }

    private boolean isUserNotExist( String userName ) {
        return userService.getUser( userName ) == null;
    }

    private void createNewUser( String userName ) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName( userName );
        userService.persistUser( userEntity );
    }

    private void integrateWithSpringSecurity( HttpServletRequest request, String userName ) {
        Authentication authentication = new UsernamePasswordAuthenticationToken( userName, "", AuthorityUtils.createAuthorityList( "ROLE_USER" ) );

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication( authentication );

        HttpSession newSession = request.getSession( true );
        newSession.setAttribute( "SPRING_SECURITY_CONTEXT", securityContext );
    }


}
