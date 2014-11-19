package tw.edu.ncu.cc.oauth.server.webservice.oauth;

//import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
//import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
//import org.apache.oltu.oauth2.as.response.OAuthASResponse;
//import org.apache.oltu.oauth2.common.error.OAuthError.TokenResponse;
//import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
//import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
//import org.apache.oltu.oauth2.common.message.OAuthResponse;
//import org.apache.oltu.oauth2.common.message.types.GrantType;
//import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
//import tw.edu.ncu.cc.oauth.server.entity.ApplicationEntity;
//import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
//import tw.edu.ncu.cc.oauth.server.entity.UserEntity;
//import tw.edu.ncu.cc.oauth.server.repository.AccessTokenRepository;
//import tw.edu.ncu.cc.oauth.server.repository.ApplicationRepository;
//import tw.edu.ncu.cc.oauth.server.repository.AuthCodeRepository;
//import tw.edu.ncu.cc.oauth.server.repository.UserRepository;
//import tw.edu.ncu.cc.oauth.server.rule.OAuthRule;
//
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Response;
//import java.util.Date;

//@Path( "token" )
public final class TokenEndPoint {

//    @Inject private UserRepository userRepository;
//    @Inject private ApplicationRepository applicationRepository;
//    @Inject private AuthCodeRepository authCodeRepository;
//    @Inject private AccessTokenRepository accessTokenRepository;
//
//    @Inject private OAuthIssuer tokenIssuer;
//
//    @POST
//    @Produces( "application/json" )
//    public Response authorize( @Context HttpServletRequest servletRequest ) throws OAuthSystemException {
//
//        OAuthResponse response;
//
//        try {
//
//            OAuthTokenRequest request = new OAuthTokenRequest( servletRequest );
//
//            checkClient   ( request );
//            checkAuthGrant( request );
//
//            response = OAuthASResponse
//                    .tokenResponse ( HttpServletResponse.SC_OK )
//                    .setAccessToken( prepareAccessToken( request ) )
//                    .buildJSONMessage();
//
//        } catch ( OAuthProblemException e ) {
//
//            response = OAuthASResponse
//                    .errorResponse( HttpServletResponse.SC_BAD_REQUEST )
//                    .error( e )
//                    .buildJSONMessage();
//        }
//
//        return Response.status( response.getResponseStatus() ).entity( response.getBody() ).build();
//    }
//
//    private void checkClient( OAuthTokenRequest request ) throws OAuthProblemException {
//
//        String clientID     = request.getClientId();
//        String clientSecret = request.getClientSecret();
//        ApplicationEntity client = applicationRepository.getApplication( Integer.parseInt( clientID ) );
//
//        if ( client == null || ! client.getSecret().equals( clientSecret ) ) {
//            throw OAuthProblemException.error( TokenResponse.INVALID_CLIENT, "INVALID CLIENT" );
//        }
//    }
//
//    private void checkAuthGrant( OAuthTokenRequest request ) throws OAuthProblemException {
//
//        String grantType = request.getGrantType();
//
//        if ( grantType.equals( GrantType.AUTHORIZATION_CODE.toString() ) ) {
//
//            if ( ! isAuthCodeValid( request.getCode(), request.getClientId() ) ) {
//                throw OAuthProblemException.error( TokenResponse.INVALID_GRANT, "INVALID AUTH CODE" );
//            }
//        } else {
//
//            throw OAuthProblemException.error( TokenResponse.UNSUPPORTED_GRANT_TYPE, "UNSUPPORTED GRANT TYPE" );
//        }
//    }
//
//    private boolean isAuthCodeValid( String authCode, String clientID ) {
//        AuthCodeEntity code = authCodeRepository.getAuthCode( authCode );
//        return isSameClient( code, clientID ) && isInValidTime( code );
//    }
//
//    private boolean isSameClient( AuthCodeEntity authCode, String clientID ) {
//        return authCode != null && ( authCode.getClient().getId() == Integer.parseInt( clientID ) );
//    }
//
//    private boolean isInValidTime( AuthCodeEntity authCode ) {
//        Date earliestValidTime = new Date( System.currentTimeMillis() - OAuthRule.CODE_EXPIRE_MILLI );
//        return authCode.getDateCreated().after( earliestValidTime );
//    }
//
//    private String prepareAccessToken( OAuthTokenRequest request )  throws OAuthSystemException {
//
//        String token = tokenIssuer.accessToken();
//
//        AuthCodeEntity authCode = authCodeRepository.getAuthCode( request.getCode() );
//
//        AccessTokenEntity accessToken = new AccessTokenEntity();
//        accessToken.setToken( token );
//        accessToken.setUser( authCode.getUser() );
//        accessToken.setClient( authCode.getClient() );
//        accessToken.setPermission( authCode.getPermission() );
//        accessTokenRepository.persistAccessToken( accessToken );
//
//        UserEntity user = authCode.getUser();
//        user.getTokens().add( accessToken );
//        userRepository.persistUsers( user );
//
//        authCodeRepository.deleteAuthCode( authCode );
//
//        return token;
//    }

}
