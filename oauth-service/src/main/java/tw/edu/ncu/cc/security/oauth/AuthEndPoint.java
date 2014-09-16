package tw.edu.ncu.cc.security.oauth;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.glassfish.jersey.server.mvc.Template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Path( "auth" ) //need a filter before this to check if logined
public class AuthEndPoint {

    @GET
    @Template( name = "/auth" )
    public Map< String, String > authorize( @Context HttpServletRequest  request ) throws URISyntaxException, OAuthSystemException {

        OAuthAuthzRequest oauthRequest = prepareOAuthRequest( request );

        checkRequest( oauthRequest );
        buildSession( oauthRequest, request );

        return prepareModel( oauthRequest );

    }

    private OAuthAuthzRequest prepareOAuthRequest( HttpServletRequest  request ) throws OAuthSystemException {

        OAuthAuthzRequest oauthRequest;

        try {
            oauthRequest    = new OAuthAuthzRequest( request );
        } catch ( OAuthProblemException e ) {
            throw new BadRequestException( "OAUTH HEADER FORMAT ERROR" );
        }

        return oauthRequest;
    }

    private void checkRequest( OAuthAuthzRequest oauthRequest ) {

        String responseType = oauthRequest.getResponseType();
        String clientState  = oauthRequest.getState();

        if( clientState == null || clientState.equals( "" ) ) {
            throw new BadRequestException( "STATE IS NOT PROVIDED" );
        }

        if( ! responseType.equals( ResponseType.CODE.toString() ) ) {
            throw new BadRequestException( "ONLY SUPPORT AUTH CODE HERE" );
        }

    }

    private void buildSession( OAuthAuthzRequest oauthRequest, HttpServletRequest request ) {
        HttpSession session = request.getSession();
        session.setAttribute( "clientID", oauthRequest.getClientId() );
        session.setAttribute( "scope"   , oauthRequest.getScopes().toString() );
    }

    private  Map< String, String > prepareModel(  OAuthAuthzRequest oauthRequest ) {
        Map< String, String > model = new HashMap<>();
        model.put( "clientID", oauthRequest.getClientId() );
        model.put( "scope"   , oauthRequest.getScopes().toString() );
        model.put( "state"   , oauthRequest.getState() );
        return model;
    }

}
