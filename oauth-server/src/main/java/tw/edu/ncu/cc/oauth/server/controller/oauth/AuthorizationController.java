package tw.edu.ncu.cc.oauth.server.controller.oauth;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import tw.edu.ncu.cc.oauth.server.entity.AccessConfirmEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.repository.ApplicationRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@SessionAttributes( "access_confirm" )
public final class AuthorizationController {

    private ApplicationRepository applicationRepository;

    @Autowired
    public void setApplicationRepository( ApplicationRepository applicationRepository ) {
        this.applicationRepository = applicationRepository;
    }

    @RequestMapping( value = "oauth/authorize", method = RequestMethod.GET )
    public String authorize( @Valid HttpServletRequest request,
                             ModelMap model,
                             BindingResult result,
                             Authentication authentication ) throws OAuthProblemException, OAuthSystemException {

        if ( result.hasErrors() ) {
            prepareFailModel( model, result );
            return "oauth_error";
        } else {
            prepareSuccessModel( request, model, authentication );
            return "oauth_approval";
        }

    }

    private void prepareFailModel( ModelMap model, BindingResult result ) {
        model.addAttribute( "errors", result.getFieldErrors() );
    }

    private void prepareSuccessModel( HttpServletRequest request, ModelMap model, Authentication authentication ) throws OAuthProblemException, OAuthSystemException {

        OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest( request );

        ClientEntity client = applicationRepository.getApplication( Integer.parseInt( oauthRequest.getClientId() ) );

        AccessConfirmEntity confirmEntity = new AccessConfirmEntity();
        confirmEntity.setState( oauthRequest.getState() );
        confirmEntity.setScope( oauthRequest.getScopes() );
        confirmEntity.setClient( client );
        confirmEntity.setUserID( authentication.getName() );

        model.addAttribute( "access_confirm", confirmEntity );
        model.addAttribute( "confirm_page", "oauth/confirm" );
    }

}
