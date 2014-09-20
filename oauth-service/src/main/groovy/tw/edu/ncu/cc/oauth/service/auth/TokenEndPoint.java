package tw.edu.ncu.cc.oauth.service.auth;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError.TokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import tw.edu.ncu.cc.oauth.db.data.AccessToken;
import tw.edu.ncu.cc.oauth.db.data.AuthCode;
import tw.edu.ncu.cc.oauth.db.data.Client;
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path( "token" )
public final class TokenEndPoint {

    @Inject private ClientModel clientModel;
    @Inject private PortalUserModel portalUserModel;
    @Inject private AuthCodeModel authCodeModel;
    @Inject private AccessTokenModel accessTokenModel;
    @Inject private PermissionModel permissionModel;
    @Inject private OAuthIssuer tokenIssuer;

    @POST
    @Consumes( "application/x-www-form-urlencoded" )
    @Produces( "application/json" )
    public Response authorize( @Context HttpServletRequest servletRequest ) throws OAuthSystemException {

        OAuthResponse response;

        try {

            OAuthTokenRequest request = new OAuthTokenRequest( servletRequest );

            checkClient   ( request );
            checkAuthGrant( request );

            response = OAuthASResponse
                    .tokenResponse ( HttpServletResponse.SC_OK )
                    .setAccessToken( prepareAccessToken( request ) )
                    .buildJSONMessage();

        } catch ( OAuthProblemException e ) {
            response = OAuthASResponse
                    .errorResponse( HttpServletResponse.SC_BAD_REQUEST )
                    .error( e )
                    .buildJSONMessage();
        }

        return Response.status( response.getResponseStatus() ).entity( response.getBody() ).build();
    }

    private void checkClient( OAuthTokenRequest request ) throws OAuthProblemException {

        if ( ! isClientValid( request.getClientId(),  request.getClientSecret() ) ) {
            throw OAuthProblemException.error( TokenResponse.INVALID_CLIENT, "INVALID CLIENT" );
        }
    }

    private boolean isClientValid( String id, String secret ) {

        Client client  = clientModel.getClient( Integer.parseInt( id ) );
        return client != null && client.getSecret().equals( secret );
    }

    private void checkAuthGrant( OAuthTokenRequest request ) throws OAuthProblemException {

        String grantType = request.getGrantType();

        if ( grantType.equals( GrantType.AUTHORIZATION_CODE.toString() ) ) {

            if ( ! isAuthCodeValid( request.getCode(), request.getClientId() ) ) {

                throw OAuthProblemException.error( TokenResponse.INVALID_GRANT, "INVALID AUTH CODE" );
            }
        } else if ( grantType.equals( GrantType.PASSWORD.toString() ) ) {

            if ( ! isUserValid( request.getUsername(), request.getPassword() ) ) {

                throw OAuthProblemException.error( TokenResponse.INVALID_GRANT, "INVALID USER" );
            }
        } else {

            throw OAuthProblemException.error( TokenResponse.UNSUPPORTED_GRANT_TYPE, "UNSUPPORTED GRANT TYPE" );
        }
    }

    private boolean isAuthCodeValid( String authCode, String clientID ) {

        AuthCode code  = authCodeModel.getAuthCode( authCode );
        return   code != null && ( code.getId() == Integer.parseInt( clientID ) );
    }

    private boolean isUserValid( String user, String password ) {
        return portalUserModel.isUserValid( user, password );
    }

    private String prepareAccessToken( OAuthTokenRequest request )  throws OAuthSystemException {

        String token = tokenIssuer.accessToken();

        AccessToken accessToken = new AccessToken();
        accessToken.setToken( token );
        accessToken.setClient( clientModel.getClient( Integer.parseInt( request.getClientId() ) ) );
        accessToken.setScope ( permissionModel.convertToPermissions( request.getScopes() ) );

        //TODO ADD TOKEN TO USER

        accessTokenModel.persistAccessToken( accessToken );

        return token;
    }

}
