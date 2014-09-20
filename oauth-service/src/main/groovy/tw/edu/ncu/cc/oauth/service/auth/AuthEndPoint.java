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
import javax.ws.rs.core.Context;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Path( "auth" ) //TODO need a filter before this to check if logined
public final class AuthEndPoint {

    @Inject private HttpSession session;
    @Inject private ClientModel clientModel;
    @Inject private PermissionModel permissionModel;

    @GET
    @Template( name = "/auth" )
    public Map< String, String > authorize( @Context HttpServletRequest  request ) throws URISyntaxException, OAuthSystemException {

        OAuthAuthzRequest oauthRequest = prepareOAuthRequest( request );

        validateRequest( oauthRequest );

        return prepareModel( oauthRequest );
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

        String responseType = oauthRequest.getResponseType();
        String clientState  = oauthRequest.getState();
        Set<String> scope   = oauthRequest.getScopes();

        if( clientState == null || clientState.equals( "" ) ) {
            throw new BadRequestException( "STATE IS NOT PROVIDED" );
        }

        if( ! responseType.equals( ResponseType.CODE.toString() ) ) {
            throw new BadRequestException( "ONLY SUPPORT AUTH CODE HERE" );
        }

        if( ! permissionModel.isPermissionsExist( scope ) ) {
            throw new BadRequestException( "PERMISSION NAME ERROR" );
        }

    }

    private  Map< String, String > prepareModel(  OAuthAuthzRequest oauthRequest ) {

        Set<String> scope = oauthRequest.getScopes();
        String state      = oauthRequest.getState();
        String clientID   = oauthRequest.getClientId();
        String clientName = getClientNameByID( clientID );

        session.setAttribute( "clientID", clientID );
        session.setAttribute( "scope",  scope );

        Map< String, String > map = new HashMap<>();
        map.put( "clientName", clientName );
        map.put( "scope", scope.toString() );
        map.put( "state", state );

        return map;
    }

    private String getClientNameByID( String clientID ) {
        Client client = clientModel.getClient( Integer.parseInt( clientID ) );
        if( client == null ) {
            throw new BadRequestException( "CLIENT NOT EXISTS" );
        }else{
            return client.getName();
        }
    }

}
