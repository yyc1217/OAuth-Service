package tw.edu.ncu.cc.oauth.server.controller.oauth;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import tw.edu.ncu.cc.oauth.server.helper.OAuthProblemBuilder;
import tw.edu.ncu.cc.oauth.server.helper.OAuthURLBuilder;
import tw.edu.ncu.cc.oauth.server.service.ClientService;
import tw.edu.ncu.cc.oauth.server.service.ScopeService;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Controller
@SessionAttributes( { "state", "scope", "client", "user_id" } )
public final class AuthorizationController {

    private ScopeService scopeService;
    private ClientService clientService;

    @Autowired
    public void setScopeService( ScopeService scopeService ) {
        this.scopeService = scopeService;
    }

    @Autowired
    public void setClientService( ClientService clientService ) {
        this.clientService = clientService;
    }


    @RequestMapping( value = "oauth/authorize", method = RequestMethod.GET )
    public String authorize( HttpServletRequest  request,
                             HttpServletResponse response,
                             Authentication authentication, ModelMap model ) throws OAuthProblemException, OAuthSystemException, IOException {

        try {

            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest( request );

            validateOauthRequest( oauthRequest );

            model.addAttribute( "state", oauthRequest.getState() );
            model.addAttribute( "scope", oauthRequest.getScopes() );
            model.addAttribute( "client", clientService.readClientByID( oauthRequest.getClientId() ) );
            model.addAttribute( "user_id", authentication.getName() );
            model.addAttribute( "confirm_page", request.getContextPath() + "/oauth/confirm" );

            return "oauth_approval";

        } catch ( OAuthSystemException e ) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST, e.getMessage() );
        } catch ( OAuthProblemException e ) {
            if( e.getRedirectUri() == null ) {
                response.sendError( HttpServletResponse.SC_BAD_REQUEST, e.getMessage() );
            } else {
                response.sendRedirect(
                        OAuthURLBuilder
                                .url( e.getRedirectUri() )
                                .state( e.getState() )
                                .error( e.getError() )
                                .errorDescription( e.getDescription() )
                                .build()
                );
            }
        }

        return null;
    }

    public void validateOauthRequest( OAuthAuthzRequest oauthRequest ) throws OAuthSystemException, OAuthProblemException {

        Set<String> scope  = oauthRequest.getScopes();
        String clientState = oauthRequest.getState();
        String clientID    = oauthRequest.getClientId();

        try {

            String callback = clientService.readClientByID( clientID ).getCallback();

            if ( ! scopeService.exist( scope ) ) {
                throw OAuthProblemBuilder
                        .error( OAuthError.CodeResponse.INVALID_SCOPE )
                        .description( "PERMISSION NOT EXISTS" )
                        .redirectUri( callback )
                        .state( clientState )
                        .build();
            }
        } catch ( NoResultException ignore ) {
            throw OAuthProblemBuilder
                    .error( OAuthError.CodeResponse.INVALID_REQUEST )
                    .description( "CLIENT NOT EXIST" )
                    .state( clientState )
                    .build();
        }

    }

}
