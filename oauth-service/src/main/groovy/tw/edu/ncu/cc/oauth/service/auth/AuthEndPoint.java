package tw.edu.ncu.cc.oauth.service.auth;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.glassfish.jersey.server.mvc.Template;
import tw.edu.ncu.cc.oauth.db.data.Client;
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.ClientModel;
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.PermissionModel;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Path( "auth" )
public final class AuthEndPoint {

    @Inject private HttpSession session;
    @Inject private ClientModel clientModel;
    @Inject private PermissionModel permissionModel;

    @GET
    @Template( name = "/auth" )
    public Map< String, String > authorize( @Context HttpServletRequest  request,
                                            @QueryParam( "portalID" ) String portalID ) throws URISyntaxException, OAuthSystemException {

        OAuthAuthzRequest oauthRequest = prepareOAuthRequest( request );

        validateRequest( oauthRequest );

        return prepareModel( oauthRequest, portalID );
    }

    private OAuthAuthzRequest prepareOAuthRequest( HttpServletRequest  request ) throws OAuthSystemException {

        OAuthAuthzRequest oauthRequest;

        try {
            oauthRequest = new OAuthAuthzRequest( request );
        } catch ( OAuthProblemException e ) {

            throw new BadRequestException( "OAUTH HEADER FORMAT ERROR" );
        }

        return oauthRequest;
    }

    private void validateRequest( OAuthAuthzRequest oauthRequest ) {

        Set<String> scope   = oauthRequest.getScopes();
        String responseType = oauthRequest.getResponseType();
        String clientState  = oauthRequest.getState();
        String clientID     = oauthRequest.getClientId();

        if( clientState == null || clientState.equals( "" ) ) {
            throw new BadRequestException( "STATE IS NOT PROVIDED" );
        }

        if( ! responseType.equals( ResponseType.CODE.toString() ) ) {
            throw new BadRequestException( "ONLY SUPPORT AUTH CODE" );
        }

        if(  clientModel.getClient( Integer.parseInt( clientID ) ) == null ) {
            throw new BadRequestException( "CLIENT NOT EXISTS" );
        }

        if( ! permissionModel.isPermissionsExist( scope ) ) {
            throw new BadRequestException( "PERMISSION NOT EXISTS" );
        }

    }

    private  Map< String, String > prepareModel(  OAuthAuthzRequest oauthRequest, String portalID ) {

        Set<String> scope = oauthRequest.getScopes();
        String state      = oauthRequest.getState();
        String clientID   = oauthRequest.getClientId();
        String clientName = getClientNameByID( clientID );

        session.setMaxInactiveInterval( 180 );
        session.setAttribute( "scope",  scope );
        session.setAttribute( "clientID", clientID );
        session.setAttribute( "portalID", portalID );

        Map< String, String > map = new HashMap<>();
        map.put( "portalID", portalID );
        map.put( "clientName", clientName );
        map.put( "scope", scope.toString() );
        map.put( "state", state );

        return map;
    }

    private String getClientNameByID( String clientID ) {
        Client client = clientModel.getClient( Integer.parseInt( clientID ) );
        return client.getName();
    }

}
