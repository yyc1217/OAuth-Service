package tw.edu.ncu.cc.oauth.server.controller.management;

//import tw.edu.ncu.cc.oauth.data.AccessToken;
//import tw.edu.ncu.cc.oauth.data.Status;
//import tw.edu.ncu.cc.oauth.data.wrapper.ResultWrapper;
//import tw.edu.ncu.cc.oauth.data.wrapper.StatusWrapper;
//import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
//import tw.edu.ncu.cc.oauth.server.repository.AccessTokenRepository;
//import tw.edu.ncu.cc.oauth.server.repository.UserRepository;
//import tw.edu.ncu.cc.oauth.server.converter.ServerAccessTokenWrapper;
//
//import javax.inject.Inject;
//import javax.ws.rs.*;

//@Path( "management/token" )
//@Produces( "application/json;charset=utf-8" )
public class TokenService {

//    @Inject
//    AccessTokenRepository accessTokenRepository;
//    @Inject
//    UserRepository userRepository;
//
//    @DELETE
//    @Path( "id/{tokenID}" )
//    public StatusWrapper getTokenScopes( @PathParam( "tokenID" ) int tokenID ) {
//        try {
//            AccessTokenEntity accessToken = accessTokenRepository.getAccessToken( tokenID );
//            accessTokenRepository.deleteAccessToken( accessToken );
//            return new StatusWrapper( Status.SUCCESS );
//        } catch ( Exception ignore ) {
//            return new StatusWrapper( Status.FAIL );
//        }
//    }
//
//    @GET
//    @Path( "user/{userID}" )
//    public ResultWrapper<AccessToken> getUserTokens( @PathParam( "userID" ) String userID ) {
//        return new ServerAccessTokenWrapper( userRepository.getUser( userID ) );
//    }


}