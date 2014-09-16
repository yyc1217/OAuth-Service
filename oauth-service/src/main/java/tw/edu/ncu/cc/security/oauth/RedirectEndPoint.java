package tw.edu.ncu.cc.security.oauth;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path( "redirect" )
public class RedirectEndPoint {

    private OAuthIssuer tokenIssuer = new OAuthIssuerImpl( new MD5Generator() );

    @GET
    public Response confirm( @Context HttpServletRequest request ) throws URISyntaxException, OAuthSystemException {

        HttpSession session = request.getSession();
        String clientID = session.getAttribute( "clientID" ).toString();
        String scope    = session.getAttribute( "scope"    ).toString();

        OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                OAuthASResponse.authorizationResponse( request, HttpServletResponse.SC_FOUND );

        builder.setCode ( prepareAuthCode( clientID, scope ) );
        builder.location( prepareRedirect( clientID ) );


        OAuthResponse response = builder.buildQueryMessage();

        return Response
                .status  ( response.getResponseStatus() )
                .location( new URI( response.getLocationUri() ) )
                .build();
    }

    private String prepareAuthCode( String clientID, String scope ) throws OAuthSystemException{
        return tokenIssuer.authorizationCode(); //TODO WRITE TO DB OR MEM WITH ID AND SCOPE
    }

    private String prepareRedirect( String clientID ) {
        return "http://example.com/callback"; //TODO READ REDIRECT URI FROM DB WHERE ID = clientID
    }

}