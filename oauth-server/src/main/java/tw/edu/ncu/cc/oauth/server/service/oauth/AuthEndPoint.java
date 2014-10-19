package tw.edu.ncu.cc.oauth.server.service.oauth;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.glassfish.jersey.server.mvc.Template;
import tw.edu.ncu.cc.oauth.server.db.data.ClientEntity;
import tw.edu.ncu.cc.oauth.server.db.model.ClientModel;
import tw.edu.ncu.cc.oauth.server.db.model.PermissionModel;
import tw.edu.ncu.cc.oauth.server.rule.LoginRule;
import tw.edu.ncu.cc.oauth.server.view.AuthBean;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.net.URISyntaxException;
import java.util.Set;

@Path( "auth" )
public final class AuthEndPoint {

    @Inject private HttpSession session;
    @Inject private ClientModel clientModel;
    @Inject private PermissionModel permissionModel;

    @GET
    @Template( name = "/auth" )
    public AuthBean authorize( @Context HttpServletRequest request ) throws URISyntaxException, OAuthSystemException {

        OAuthAuthzRequest oauthRequest = prepareOAuthRequest( request );

        validateRequest( oauthRequest );

        return prepareModel( oauthRequest );
    }

    private OAuthAuthzRequest prepareOAuthRequest( HttpServletRequest request ) throws OAuthSystemException {

        OAuthAuthzRequest oauthRequest;

        try {
            oauthRequest = new OAuthAuthzRequest( request );
        } catch ( OAuthProblemException e ) {
            throw new BadRequestException( "OAUTH HEADER FORMAT ERROR : " + e.getDescription() );
        }
        return oauthRequest;
    }

    private void validateRequest( OAuthAuthzRequest oauthRequest ) {

        Set<String> scope   = oauthRequest.getScopes();
        String responseType = oauthRequest.getResponseType();
        String clientState  = oauthRequest.getState();
        String clientID     = oauthRequest.getClientId();

        if ( clientState == null || clientState.equals( "" ) ) {
            throw new BadRequestException( "STATE IS NOT PROVIDED" );
        }
        if ( ! responseType.equals( ResponseType.CODE.toString() ) ) {
            throw new BadRequestException( "ONLY SUPPORT AUTH CODE" );
        }
        if ( clientModel.getClient( Integer.parseInt( clientID ) ) == null ) {
            throw new BadRequestException( "CLIENT NOT EXISTS" );
        }
        if ( ! permissionModel.isPermissionsExist( scope ) ) {
            throw new BadRequestException( "PERMISSION NOT EXISTS" );
        }
    }

    private AuthBean prepareModel( OAuthAuthzRequest request ) {

        ClientEntity client = clientModel.getClient( Integer.parseInt( request.getClientId() ) );

        AuthBean authBean = new AuthBean( session );
        authBean.setScope( request.getScopes() );
        authBean.setState( request.getState() );
        authBean.setPortalID( session.getAttribute( LoginRule.KEY_LOGIN_ID ).toString() );
        authBean.setClientID( request.getClientId() );
        authBean.setClientURL( client.getUrl() );
        authBean.setClientName( client.getName() );

        return authBean;
    }

}
