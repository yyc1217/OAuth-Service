package tw.edu.ncu.cc.oauth.server.controller.oauth;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.model.AccessConfirmModel;
import tw.edu.ncu.cc.oauth.server.service.ClientService;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes( "access_confirm" )
public final class AuthorizationController {

    private ClientService clientService;

    @Autowired
    public void setClientService( ClientService clientService ) {
        this.clientService = clientService;
    }

    @RequestMapping( value = "oauth/authorize", method = RequestMethod.GET )
    public String authorize( HttpServletRequest request, ModelMap model, Authentication authentication ) throws OAuthProblemException, OAuthSystemException {

        OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest( request );

        ClientEntity client = clientService.getClient( Integer.parseInt( oauthRequest.getClientId() ) );

        AccessConfirmModel confirmEntity = new AccessConfirmModel();
        confirmEntity.setState( oauthRequest.getState() );
        confirmEntity.setScope( oauthRequest.getScopes() );
        confirmEntity.setClient( client );
        confirmEntity.setUserID( authentication.getName() );

        model.addAttribute( "access_confirm", confirmEntity );
        model.addAttribute( "confirm_page", request.getContextPath() + "/oauth/confirm" );

        return "oauth_approval";

    }

}
