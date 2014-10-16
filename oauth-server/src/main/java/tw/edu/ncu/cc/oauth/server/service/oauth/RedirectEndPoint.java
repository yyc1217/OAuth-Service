package tw.edu.ncu.cc.oauth.server.service.oauth;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import tw.edu.ncu.cc.oauth.server.db.data.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.db.data.ClientEntity;
import tw.edu.ncu.cc.oauth.server.db.data.UserEntity;
import tw.edu.ncu.cc.oauth.server.db.model.AuthCodeModel;
import tw.edu.ncu.cc.oauth.server.db.model.ClientModel;
import tw.edu.ncu.cc.oauth.server.db.model.PermissionModel;
import tw.edu.ncu.cc.oauth.server.db.model.UserModel;
import tw.edu.ncu.cc.oauth.server.view.AuthBean;

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

    @Inject private HttpSession session;

    @Inject private UserModel   userModel;
    @Inject private ClientModel  clientModel;
    @Inject private AuthCodeModel authCodeModel;
    @Inject private PermissionModel permissionModel;

    @Inject private OAuthIssuer tokenIssuer;

    @GET
    public Response confirm( @Context HttpServletRequest request ) throws URISyntaxException, OAuthSystemException {

        AuthBean authBean = new AuthBean( session );

        validateData( authBean );

        return prepareResponse( authBean, request );
    }

    private void validateData( AuthBean authBean ) {
        try{
            authBean.getScope();
            authBean.getPortalID();
            authBean.getClientID();
        } catch ( NullPointerException ignore ) {
            throw new BadRequestException( "DATA FETCH ERROR" );
        }
        if( clientModel.getClient( Integer.parseInt( authBean.getClientID() ) ) == null ) {
            throw new BadRequestException( "CLIENT NOT EXISTS" );
        }
    }

    private Response prepareResponse( AuthBean authBean, HttpServletRequest request ) throws URISyntaxException, OAuthSystemException  {

        String clientID = authBean.getClientID();
        String portalID = authBean.getPortalID();
        Set<String> scopes = authBean.getScope();
        authBean.destroy();

        OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                OAuthASResponse.authorizationResponse( request, HttpServletResponse.SC_FOUND );

        ClientEntity client = clientModel.getClient( Integer.parseInt( clientID ) );

        builder.setCode ( prepareAuthCode( client, portalID, scopes ) );
        builder.location( prepareRedirect( client ) );

        OAuthResponse response = builder.buildQueryMessage();

        return Response
                .status( response.getResponseStatus() )
                .location( new URI( response.getLocationUri() ) )
                .build();
    }

    private String prepareAuthCode( ClientEntity client, String portalID, Set<String> scopes ) throws OAuthSystemException{

        UserEntity user = userModel.getUser( portalID );

        AuthCodeEntity authCode = new AuthCodeEntity();
        authCode.setClient( client );
        authCode.setUser( user );
        authCode.setCode  ( tokenIssuer.authorizationCode() );
        authCode.setScope ( permissionModel.convertToPermissions( scopes ) );
        authCodeModel.persistAuthCodes( authCode );

        return authCode.getCode();
    }

    private String prepareRedirect( ClientEntity client ) {
        return client.getCallback();
    }

}