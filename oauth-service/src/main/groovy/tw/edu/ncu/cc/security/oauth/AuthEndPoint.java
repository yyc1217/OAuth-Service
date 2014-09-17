package tw.edu.ncu.cc.security.oauth;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.glassfish.jersey.server.mvc.Template;
import org.hibernate.Session;
import tw.edu.ncu.cc.security.data.Helper.PermissionHelper;
import tw.edu.ncu.cc.security.oauth.db.HibernateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Path( "auth" ) //TODO need a filter before this to check if logined
public class AuthEndPoint {

    @GET
    @Template( name = "/auth" )
    public Map< String, String > authorize( @Context HttpServletRequest  request ) throws URISyntaxException, OAuthSystemException {

        OAuthAuthzRequest oauthRequest = prepareOAuthRequest( request );

        validateRequest( oauthRequest );

        return prepareModel( oauthRequest, request );
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

        if( ! PermissionHelper.isAllPermissionExist( scope ) ) {
            throw new BadRequestException( "PERMISSION NAME ERROR" );
        }

    }

    private  Map< String, String > prepareModel(  OAuthAuthzRequest oauthRequest, HttpServletRequest request ) {

        Set<String> scope = oauthRequest.getScopes();
        String state      = oauthRequest.getState();
        String clientID   = oauthRequest.getClientId();
        String clientName = getClientNameByID( clientID );

        HttpSession session = request.getSession();
        session.setAttribute( "clientID", clientID );
        session.setAttribute( "scope",  scope.toString() );

        Map< String, String > model = new HashMap<>();
        model.put( "clientName", clientName );
        model.put( "scope",  scope.toString() );
        model.put( "state",  state );

        return model;
    }

    private String getClientNameByID( String clientID ) {

        Session session = HibernateUtil.currentSession();

        List names = session
                .createQuery( "select name from Client where id = :id " )
                .setString( "id", clientID )
                .list();

        if( names.size() != 1 ) {
            throw new BadRequestException( "CLIENT NOT EXISTS" );
        }

        return ( String ) names.get( 0 );
    }

}
