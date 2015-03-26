package tw.edu.ncu.cc.oauth.server.web.oauth

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest
import org.apache.oltu.oauth2.common.error.OAuthError
import org.apache.oltu.oauth2.common.exception.OAuthProblemException
import org.apache.oltu.oauth2.common.exception.OAuthSystemException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.SessionAttributes
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.Permission
import tw.edu.ncu.cc.oauth.server.helper.OAuthProblemBuilder
import tw.edu.ncu.cc.oauth.server.helper.OAuthURLBuilder
import tw.edu.ncu.cc.oauth.server.service.domain.ClientService
import tw.edu.ncu.cc.oauth.server.service.domain.PermissionService

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@SessionAttributes( [ "state", "scope", "client" ] )
public final class AuthorizationController {

    @Autowired
    def ClientService clientService;

    @Autowired
    def PermissionService permissionService

    private Logger logger = LoggerFactory.getLogger( this.getClass() )

    @RequestMapping( value = "oauth/authorize", method = RequestMethod.GET )
    public String authorize( HttpServletRequest  request,
                             HttpServletResponse response,
                             Authentication authentication, ModelMap model ) throws OAuthProblemException, OAuthSystemException, IOException {

        try {

            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest( request );

            logger.info(
                    String.format(
                            "ACCESS REQUEST, USER: %s, CLIENT: %s, SCOPE: %s ",
                            authentication.name,
                            oauthRequest.clientId,
                            oauthRequest.scopes.toListString()
                    )
            )

            validateOauthRequest( oauthRequest );

            model.addAttribute( "state", oauthRequest.getState() == null ? "" : oauthRequest.getState() );
            model.addAttribute( "scope", convertToPermissions( oauthRequest.getScopes() ) );
            model.addAttribute( "client", clientService.readBySerialId( oauthRequest.getClientId() ) );
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

        Client client = clientService.readBySerialId( clientID )
        if( client == null ) {
            throw OAuthProblemBuilder
                    .error( OAuthError.CodeResponse.INVALID_REQUEST )
                    .description( "CLIENT NOT EXIST" )
                    .state( clientState )
                    .build();
        }

        if ( ! isScopeExist( scope ) ) {
            throw OAuthProblemBuilder
                    .error( OAuthError.CodeResponse.INVALID_SCOPE )
                    .description( "PERMISSION NOT EXISTS" )
                    .redirectUri( client.callback )
                    .state( clientState )
                    .build();
        }

    }

    private boolean isScopeExist( Set< String > scope ) {
        for( String permission : scope ) {
            if( permissionService.readByName( permission ) == null ) {
                return false
            }
        }
        return true
    }

    private Set< Permission > convertToPermissions( Set< String > scope ) {
        scope.inject( [] as Set< Permission > ) { permissions, permissionName ->
            permissions << permissionService.readByName( permissionName )
        } as Set<Permission>
    }

}