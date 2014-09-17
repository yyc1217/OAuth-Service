package tw.edu.ncu.cc.security.service;

import javax.ws.rs.*;

@Path( "microservice" )
public class Microservice {

    @GET
    @Path( "token/{token}" )
    public String getTokenScopes( @PathParam( "token" ) String token ) {
//        scope:["class"]
        return null;
    }

    @DELETE
    @Path( "token/{tokenID}" )
    public String deleteToken( @PathParam( "tokenID" ) int tokenID ) {
//        state:200
        return null;
    }

    @GET
    @Path( "user/{id}" )
    public String getUserTokens( @PathParam( "id" ) int userID ) {
//        tokens:[
//            {
//                id:123,
//                client:"NCUClass",
//                scope:["class"]
//            },
//            {
//                id:321,
//                client:"NCUMap",
//                scope:["class:read"]
//            }
//        ]
        return null;
    }

    @POST
    @Path( "register/tokenConsumer/{callback}" )
    public String registerTokenConsumer( @PathParam( "callback" ) String callback ) {
//        state:200
        return null;
    }

}
