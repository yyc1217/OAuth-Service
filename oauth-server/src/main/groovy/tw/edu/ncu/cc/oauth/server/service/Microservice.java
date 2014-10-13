package tw.edu.ncu.cc.oauth.server.service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path( "microservice" )
public class Microservice {

    @GET
    @Path( "token/{token}" )
    @Produces( "application/json" )
    public String getTokenScopes( @PathParam( "token" ) String token ) {
//        scope:["class"]
        return null;
    }

    @DELETE
    @Path( "token/{tokenID}" )
    public Response deleteToken( @PathParam( "tokenID" ) int tokenID ) {
//        state:200
        return null;
    }

    @GET
    @Path( "user/{id}" )
    @Produces( "application/json" )
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

}
