package tw.edu.ncu.cc.oauth.server.controller.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tw.edu.ncu.cc.manage.openid.OpenIDManager;
import tw.edu.ncu.cc.oauth.server.entity.UserEntity;
import tw.edu.ncu.cc.oauth.server.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    private OpenIDManager openIDManager;
    private UserRepository userRepository;

    @Autowired
    public void setOpenIDManager( OpenIDManager openIDManager ) {
        this.openIDManager = openIDManager;
    }

    @Autowired
    public void setUserRepository( UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @RequestMapping( value = "login", method = RequestMethod.GET )
    public String authorize( ModelMap model ){
        model.addAttribute( "openidLoginPage", openIDManager.getURLString() );
        return "login";
    }

    @RequestMapping( value = "login_confirm", method = RequestMethod.GET )
    public String confirm( HttpServletRequest request, HttpSession session ){

        SavedRequest savedRequest = getSavedRequest( session );
        Map< String, ? > map      = request.getParameterMap();

        try {
            if( hasPreviousPage( savedRequest ) ) {

                if( isOpenIDHeaderCorrect( map ) ) {

                    String studentID = openIDManager.getStudentID( map );

                    if( isUserNotExist( studentID ) ) {
                        createNewUser ( studentID );
                    }
                    integrateWithSpringSecurity( request, studentID );

                } else {
                    throw new IllegalStateException( "given data is incorrect" );
                }
            } else {
                throw new IllegalStateException( "no previous url is incorrect" );
            }
        } catch ( IllegalStateException e ) {
            throw new RuntimeException( "login with inappropriate way", e );
        }

        return "redirect:" + savedRequest.getRedirectUrl();
    }

    private SavedRequest getSavedRequest( HttpSession session ) {
        return ( SavedRequest ) session.getAttribute( "SPRING_SECURITY_SAVED_REQUEST" );
    }

    private boolean hasPreviousPage( SavedRequest savedRequest ) {
        return savedRequest != null;
    }

    private boolean isOpenIDHeaderCorrect( Map< String, ? > map ) {
        return openIDManager.checkAuthentication( map );
    }

    private boolean isUserNotExist( String userName ) {
        return userRepository.getUser( userName ) == null;
    }

    private void createNewUser( String userName ) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName( userName );
        userRepository.persistUser( userEntity );
    }

    private Authentication prepareAuthentication( String userName ) {
        return new UsernamePasswordAuthenticationToken( userName, "", AuthorityUtils.createAuthorityList( "ROLE_USER" ) );
    }

    private void integrateWithSpringSecurity( HttpServletRequest request, String userName ) {
        Authentication authentication = prepareAuthentication( userName );

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication( authentication );

        HttpSession newSession = request.getSession( true );
        newSession.setAttribute( "SPRING_SECURITY_CONTEXT", securityContext );
    }

}
