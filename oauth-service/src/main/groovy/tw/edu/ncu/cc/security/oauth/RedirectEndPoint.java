package tw.edu.ncu.cc.security.oauth;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tw.edu.ncu.cc.security.data.AuthCode;
import tw.edu.ncu.cc.security.data.Client;
import tw.edu.ncu.cc.security.data.Helper.PermissionHelper;
import tw.edu.ncu.cc.security.oauth.db.HibernateUtil;

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
import java.util.List;

@Path( "redirect" )
public class RedirectEndPoint {

    private OAuthIssuer tokenIssuer = new OAuthIssuerImpl( new MD5Generator() );

    @GET
    public Response confirm( @Context HttpServletRequest request ) throws URISyntaxException, OAuthSystemException {

        HttpSession session = request.getSession();
        String clientID    = session.getAttribute( "clientID" ).toString();
        String scopeString = session.getAttribute( "scope" ).toString();

        Client client      = getClientByID( clientID );

        return prepareResponse( request, client, scopeString );
    }

    private Client getClientByID( String clientID ) {

        Session session = HibernateUtil.currentSession();

        List names = session
                .createQuery( "from Client where id = :id " )
                .setString( "id", clientID )
                .list();

        if( names.size() != 1 ) {
            throw new BadRequestException( "CLIENT NOT EXISTS" );
        }

        return ( Client ) names.get( 0 );
    }

    private Response prepareResponse( HttpServletRequest request, Client client, String scopeString ) throws URISyntaxException, OAuthSystemException  {

        OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                OAuthASResponse.authorizationResponse( request, HttpServletResponse.SC_FOUND );

        builder.setCode ( prepareAuthCode( client, scopeString ) );
        builder.location( prepareRedirect( client ) );

        OAuthResponse response = builder.buildQueryMessage();

        return Response
                .status( response.getResponseStatus() )
                .location( new URI( response.getLocationUri() ) )
                .build();
    }

    private String prepareAuthCode( Client client, String scopeString ) throws OAuthSystemException{

        AuthCode authCode = new AuthCode();
        authCode.setClient( client );
        authCode.setCode  ( tokenIssuer.authorizationCode() );
        authCode.setScope ( PermissionHelper.convertStringToPermissions( scopeString ) );

        saveAuthCode( authCode );

        return authCode.getCode();
    }

    private void saveAuthCode( AuthCode authCode ) {
        Session session = HibernateUtil.currentSession();

        Transaction transaction = session.beginTransaction();

        session.save( authCode );

        transaction.commit();
    }

    private String prepareRedirect( Client client ) {
        return client.getCallback();
    }

}