package tw.edu.ncu.cc.oauth.server.webservice.login;

//import tw.edu.ncu.cc.manage.openid.OpenIDManager;
//import tw.edu.ncu.cc.oauth.server.entity.UserEntity;
//import tw.edu.ncu.cc.oauth.server.repository.UserRepository;
//import tw.edu.ncu.cc.oauth.server.rule.LoginRule;
//
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.BadRequestException;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Response;
//import java.net.URI;
//import java.util.Map;

//@Path( "login" )
public class LoginEndPoint {

//    @Inject OpenIDManager openIDManager;
//    @Inject
//    UserRepository userRepository;
//    @Context HttpServletRequest request;
//
//    @GET
//    public Response handlePortalResponse() {
//
//        Map response = getResponse();
//
//        if( isResponseCorrect( response ) ) {
//            String portalID    = getStudentID( response );
//            String queryString = getStoredQueryString();
//            initUserIfNotCreate( portalID );
//            initUserLoginStatus( portalID );
//            return toAuthPageWithParam( queryString );
//        } else {
//            throw new BadRequestException( "openid validation error" );
//        }
//    }
//
//    private Map getResponse() {
//        return request.getParameterMap();
//    }
//
//    private boolean isResponseCorrect( Map response ) {
//        return openIDManager.checkAuthentication( response );
//    }
//
//    private String getStudentID( Map response ) {
//        return openIDManager.getStudentID( response );
//    }
//
//    private String getStoredQueryString() {
//        return request.getSession().getAttribute( LoginRule.KEY_QUERY_STRING ).toString();
//    }
//
//    private void initUserIfNotCreate( String portalID ) {
//        if( userRepository.getUser( portalID ) == null ) {
//            userRepository.persistUsers( new UserEntity( portalID ) );
//        }
//    }
//
//    private void initUserLoginStatus( String portalID ) {
//        request.getSession().invalidate();
//        request.getSession().setAttribute( LoginRule.KEY_LOGIN_ID, portalID );
//    }
//
//    private Response toAuthPageWithParam( String queryString ) {
//        return Response.temporaryRedirect(
//                URI.create( LoginRule.PATH_AUTH + "?" + queryString )
//        ).build();
//    }

}
