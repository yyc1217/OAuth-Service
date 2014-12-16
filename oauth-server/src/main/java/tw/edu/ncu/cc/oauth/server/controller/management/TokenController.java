package tw.edu.ncu.cc.oauth.server.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessToken;
import tw.edu.ncu.cc.oauth.server.exception.handler.APIExceptionHandler;
import tw.edu.ncu.cc.oauth.server.helper.ResponseBuilder;
import tw.edu.ncu.cc.oauth.server.service.AccessTokenAPIService;


@RestController
@RequestMapping( value = "management/v1/token" )
public class TokenController extends APIExceptionHandler {

    private ConversionService conversionService;
    private AccessTokenAPIService accessTokenAPIService;

    @Autowired
    public void setConversionService( ConversionService conversionService ) {
        this.conversionService = conversionService;
    }

    @Autowired
    public void setAccessTokenAPIService( AccessTokenAPIService accessTokenAPIService ) {
        this.accessTokenAPIService = accessTokenAPIService;
    }

    @RequestMapping( value = "{id}", method = RequestMethod.GET )
    public ResponseEntity getToken( @PathVariable( "id" ) final String id ) {
        return ResponseBuilder
                .noneValidation()
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                accessTokenAPIService.readAccessTokenByID( id ), AccessToken.class
                        );
                    }
                } )
                .build();
    }

    @RequestMapping( value = "{id}", method = RequestMethod.DELETE )
    public ResponseEntity deleteToken( @PathVariable( "id" ) final String id ) {
        return ResponseBuilder
                .noneValidation()
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                accessTokenAPIService.revokeAccessTokenByID( id ), AccessToken.class
                        );
                    }
                } )
                .build();
    }

    @RequestMapping( value = "string/{token}", method = RequestMethod.GET )
    public ResponseEntity getTokenScope( @PathVariable( "token" ) final String token ) {
        return ResponseBuilder
                .noneValidation()
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                accessTokenAPIService.readAccessTokenByToken( token ), AccessToken.class
                        );
                    }
                } )
                .build();
    }

}
