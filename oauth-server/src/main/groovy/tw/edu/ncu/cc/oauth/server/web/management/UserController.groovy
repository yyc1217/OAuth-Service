package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.TypeDescriptor
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClientObject
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.data.v1.management.user.UserObject
import tw.edu.ncu.cc.oauth.server.domain.AccessToken
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.User
import tw.edu.ncu.cc.oauth.server.service.domain.AccessTokenService
import tw.edu.ncu.cc.oauth.server.service.domain.UserService

import static tw.edu.ncu.cc.oauth.server.helper.Responder.resource
import static tw.edu.ncu.cc.oauth.server.helper.Responder.respondWith

@RestController
@RequestMapping( value = "management/v1/user" )
public class UserController {

    @Autowired
    def UserService userService;

    @Autowired
    def AccessTokenService accessTokenService

    @Autowired
    def ConversionService conversionService;

    @SuppressWarnings( "unchecked" )
    @RequestMapping( value = "{userName}/token", method = RequestMethod.GET )
    public ResponseEntity getUserTokens( @PathVariable( "userName" ) final String userName ) {
        respondWith(
            resource()
            .pipe {
                userService.readByName( userName )
            }.pipe {
                conversionService.convert(
                        accessTokenService.readAllUnexpiredByUserName( userName, [ 'user', 'scope' ] ),
                        TypeDescriptor.collection( List.class, TypeDescriptor.valueOf( AccessToken.class ) ),
                        TypeDescriptor.array( TypeDescriptor.valueOf( AccessTokenObject.class ) )
                );
            }
        )
    }

    @SuppressWarnings( "unchecked" )
    @RequestMapping( value = "{userName}/application", method = RequestMethod.GET )
    public ResponseEntity getUserApplications( @PathVariable( "userName" ) final String userName ) {
        respondWith(
            resource()
            .pipe {
                userService.readByName( userName, [ 'clients' ] )
            }.pipe { User user ->
                conversionService.convert(
                        user.clients,
                        TypeDescriptor.collection( Set.class, TypeDescriptor.valueOf( Client.class ) ),
                        TypeDescriptor.array( TypeDescriptor.valueOf( IdClientObject.class ) )
                );
            }
        )
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity createUserIfNotExist( @Validated @RequestBody final UserObject userObject, BindingResult validation ) {
        respondWith(
            resource()
            .validate( validation )
            .pipe {
                conversionService.convert(
                        userService.createWithNameIfNotExist( userObject.getName() ), UserObject.class
                );
            }
        )
    }

}