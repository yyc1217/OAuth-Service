package tw.edu.ncu.cc.oauth.service.auth;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.jboss.logging.Logger;
import tw.edu.ncu.cc.oauth.db.data.AuthCode;
import tw.edu.ncu.cc.oauth.db.data.Client;
import tw.edu.ncu.cc.oauth.db.data.User;
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.AuthCodeModel;
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.ClientModel;
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.PermissionModel;
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.UserModel;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

@Path( "redirect" )
public final class RedirectEndPoint {

    @Inject private PermissionModel permissionModel;
    @Inject private AuthCodeModel authCodeModel;
    @Inject private UserModel userModel;
    @Inject private ClientModel clientModel;
    @Inject private HttpSession session;
    @Inject private OAuthIssuer tokenIssuer;

    @GET
    public Response confirm( @Context HttpServletRequest request ) throws URISyntaxException, OAuthSystemException {

        @SuppressWarnings( "unchecked" )
        Set<String> scopes = ( Set<String> ) session.getAttribute( "scope" );
        String clientID    = ( String ) session.getAttribute( "clientID" );
        String portalID    = ( String ) session.getAttribute( "portalID" );

        validateData( scopes, clientID, portalID );

        return prepareResponse( request, clientID, portalID, scopes );
    }

    private void validateData( Set<String> scopes, String clientID, String portalID ) {
        if( scopes == null || clientID == null || portalID == null ) {
            Logger.getLogger( "RED" ).error( "CANNOT FETCH ATTRIBUTE" );
            throw new BadRequestException( "CANNOT FETCH ATTRIBUTE" );
        }

        if( clientModel.getClient( Integer.parseInt( clientID ) ) == null ) {
            Logger.getLogger( "RED" ).error( "CLIENT NOT EXISTS" );
            throw new BadRequestException( "CLIENT NOT EXISTS" );
        }
    }

    private Response prepareResponse( HttpServletRequest request, String clientID,
                                      String portalID, Set<String> scopes ) throws URISyntaxException, OAuthSystemException  {

        OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                OAuthASResponse.authorizationResponse( request, HttpServletResponse.SC_FOUND );

        Client client = clientModel.getClient( Integer.parseInt( clientID ) );

        builder.setCode ( prepareAuthCode( client, portalID, scopes ) );
        builder.location( prepareRedirect( client ) );

        OAuthResponse response = builder.buildQueryMessage();

        return Response
                .status( response.getResponseStatus() )
                .location( new URI( response.getLocationUri() ) )
                .build();
    }

    private String prepareAuthCode( Client client, String portalID, Set<String> scopes ) throws OAuthSystemException{

        User user = userModel.getUser( portalID );

        AuthCode authCode = new AuthCode();
        authCode.setClient( client );
        authCode.setUser( user );
        authCode.setCode  ( tokenIssuer.authorizationCode() );
        authCode.setScope ( permissionModel.convertToPermissions( scopes ) );
        authCodeModel.persistAuthCodes( authCode );

        return authCode.getCode();
    }

    private String prepareRedirect( Client client ) {
        return client.getCallback();
    }

}