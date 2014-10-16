package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.manage.openid.OpenIDManager;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;

@Path( "login" )
public class LoginEndPoint {

    @Inject OpenIDManager openIDManager;

    @GET
    public Response login( @Context HttpServletRequest request ) {

        Map responseParams = request.getParameterMap();

        if( openIDManager.checkAuthentication( responseParams ) ) {
            HttpSession session = request.getSession();
            String portalID = openIDManager.getStudentID( responseParams );
            String path = session.getAttribute( "queryString" ).toString();
            session.invalidate();
            session = request.getSession();
            session.setAttribute( "portalID", portalID );
            return Response.temporaryRedirect( URI.create( "auth/" + path ) ).build();
        } else {
            throw new BadRequestException( "openid validation error" );
        }
    }


}
