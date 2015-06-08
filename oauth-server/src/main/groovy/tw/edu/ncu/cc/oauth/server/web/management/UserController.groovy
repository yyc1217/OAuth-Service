package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.TypeDescriptor
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClientObject
import tw.edu.ncu.cc.oauth.data.v1.management.token.ClientTokenObject
import tw.edu.ncu.cc.oauth.data.v1.management.user.DetailedUserObject
import tw.edu.ncu.cc.oauth.data.v1.management.user.UserObject
import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshToken
import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshTokenService
import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshToken_
import tw.edu.ncu.cc.oauth.server.concepts.user.User
import tw.edu.ncu.cc.oauth.server.concepts.user.UserService
import tw.edu.ncu.cc.oauth.server.concepts.user.UserValidator
import tw.edu.ncu.cc.oauth.server.concepts.user.User_

import static tw.edu.ncu.cc.oauth.server.helper.Responder.resource
import static tw.edu.ncu.cc.oauth.server.helper.Responder.respondWith

@RestController
@RequestMapping( value = "management/v1/users" )
public class UserController {

    @Autowired
    def UserService userService

    @Autowired
    def RefreshTokenService refreshTokenService

    @Autowired
    def ConversionService conversionService

    @InitBinder
    public static void initBinder( WebDataBinder binder ) {
        binder.addValidators( new UserValidator() );
    }

    @RequestMapping( value = "{userName}", method = RequestMethod.GET )
    public ResponseEntity getUser( @PathVariable( "userName" ) final String userName ) {
        respondWith(
                resource()
                .pipe {
                    conversionService.convert(
                            userService.findByName( userName ), DetailedUserObject.class
                    );
                }
        )
    }

    @RequestMapping( value = "{userName}/authorized_tokens", method = RequestMethod.GET )
    public ResponseEntity getAccessTokens( @PathVariable( "userName" ) final String userName ) {
        respondWith(
            resource()
            .pipe {
                userService.findByName( userName )
            }.pipe { User user ->
                conversionService.convert(
                        refreshTokenService.readAllUnexpiredByUser( user, RefreshToken_.scope ),
                        TypeDescriptor.collection( List.class, TypeDescriptor.valueOf( RefreshToken.class ) ),
                        TypeDescriptor.array( TypeDescriptor.valueOf( ClientTokenObject.class ) )
                );
            }
        )
    }

    @RequestMapping( value = "{userName}/clients", method = RequestMethod.GET )
    public ResponseEntity getOwnedClients( @PathVariable( "userName" ) final String userName ) {
        respondWith(
            resource()
            .pipe {
                userService.findByName( userName, User_.clients )
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
    public ResponseEntity createIfNotExist( @Validated @RequestBody final UserObject userObject, BindingResult validation ) {
        respondWith(
            resource()
            .validate( validation )
            .pipe {
                conversionService.convert(
                        userService.createByNameIfNotExist( userObject.getName() ), UserObject.class
                );
            }
        )
    }

}