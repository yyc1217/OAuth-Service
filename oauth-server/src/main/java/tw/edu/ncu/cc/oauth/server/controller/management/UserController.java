package tw.edu.ncu.cc.oauth.server.controller.management;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ncu.cc.oauth.data.v1.management.application.IdApplication;
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessToken;

import java.util.Date;

@RestController
@RequestMapping( value = "management/v1/user" )
public class UserController {

    @RequestMapping( value = "{userName}/token", method = RequestMethod.GET )
    public ResponseEntity getUserTokens( @PathVariable( "userName" ) String user ) {

        IdApplication application = new IdApplication();
        application.setId( "1" );
        application.setName( "demo" );
        application.setDescription( "description" );
        application.setUrl( "http://example.com" );
        application.setCallback( "http://example.com" );
        application.setOwner( "101502550" );

        AccessToken accessToken = new AccessToken();
        accessToken.setId( "1" );
        accessToken.setUser( user );
        accessToken.setScope( new String[]{ "READ", "WRITE" } );
        accessToken.setLast_updated( new Date() );
        accessToken.setApplication( application );

        return new ResponseEntity<>( new AccessToken[]{ accessToken }, HttpStatus.OK );
    }
}
