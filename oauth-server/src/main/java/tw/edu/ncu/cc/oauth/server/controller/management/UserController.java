package tw.edu.ncu.cc.oauth.server.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tw.edu.ncu.cc.oauth.data.v1.management.application.IdApplication;
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessToken;
import tw.edu.ncu.cc.oauth.data.v1.management.user.User;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.exception.handler.APIExceptionHandler;
import tw.edu.ncu.cc.oauth.server.helper.ResponseBuilder;
import tw.edu.ncu.cc.oauth.server.service.UserAPIService;

import java.util.Set;

@RestController
@RequestMapping( value = "management/v1/user" )
public class UserController extends APIExceptionHandler {
    
    private UserAPIService userAPIService;
    private ConversionService conversionService;

    @Autowired
    public void setUserAPIService( UserAPIService userAPIService ) {
        this.userAPIService = userAPIService;
    }

    @Autowired
    public void setConversionService( ConversionService conversionService ) {
        this.conversionService = conversionService;
    }

    @SuppressWarnings( "unchecked" )
    @RequestMapping( value = "{userName}/token", method = RequestMethod.GET )
    public ResponseEntity getUserTokens( @PathVariable( "userName" ) final String userName ) {
        return ResponseBuilder
                .noneValidation()
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                userAPIService.readUserTokens( userName ),
                                TypeDescriptor.collection( Set.class, TypeDescriptor.valueOf( AccessTokenEntity.class ) ),
                                TypeDescriptor.array( TypeDescriptor.valueOf( AccessToken.class ) )
                        );
                    }
                } )
                .build();
    }

    @SuppressWarnings( "unchecked" )
    @RequestMapping( value = "{userName}/application", method = RequestMethod.GET )
    public ResponseEntity getUserApplications( @PathVariable( "userName" ) final String userName ) {
        return ResponseBuilder
                .noneValidation()
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                userAPIService.readUserClients( userName ),
                                TypeDescriptor.collection( Set.class, TypeDescriptor.valueOf( ClientEntity.class ) ),
                                TypeDescriptor.array( TypeDescriptor.valueOf( IdApplication.class ) )
                        );
                    }
                } )
                .build();
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity createUser( @Validated @RequestBody final User user, BindingResult result ) {
        return ResponseBuilder
                .validation()
                .errors( result )
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                userAPIService.createUser( user.getName() ), User.class
                        );
                    }
                } )
                .build();
    }

}
