package tw.edu.ncu.cc.oauth.server.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tw.edu.ncu.cc.oauth.data.v1.management.application.Application;
import tw.edu.ncu.cc.oauth.data.v1.management.application.IdApplication;
import tw.edu.ncu.cc.oauth.data.v1.management.application.SecretIdApplication;
import tw.edu.ncu.cc.oauth.data.v1.message.Error;
import tw.edu.ncu.cc.oauth.data.v1.message.ErrorCode;
import tw.edu.ncu.cc.oauth.server.helper.ResponseBuilder;
import tw.edu.ncu.cc.oauth.server.service.ClientFactory;
import tw.edu.ncu.cc.oauth.server.service.ClientService;
import tw.edu.ncu.cc.oauth.server.validator.ApplicationValidator;

@RestController
@RequestMapping( value = "management/v1/application" )
public class ApplicationController {

    private ClientService clientService;
    private ClientFactory clientFactory;
    private ConversionService conversionService;

    @Autowired
    public void setClientService( ClientService clientService ) {
        this.clientService = clientService;
    }

    @Autowired
    public void setClientFactory( ClientFactory clientFactory ) {
        this.clientFactory = clientFactory;
    }

    @Autowired
    public void setConversionService( ConversionService conversionService ) {
        this.conversionService = conversionService;
    }

    @InitBinder
    public void initBinder( WebDataBinder binder ) {
        binder.addValidators( new ApplicationValidator() );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity createApplication( @RequestBody @Validated Application application, BindingResult result ) {
        if( result.hasErrors() ) {
            return new ResponseEntity<>(
                    new Error(
                            ErrorCode.INVALID_FIELD, result.getFieldError().getDefaultMessage()
                    ), HttpStatus.BAD_REQUEST
            );
        } else {
            return new ResponseEntity<>(
                    conversionService.convert(
                            clientFactory.createClient( application ), SecretIdApplication.class
                    ), HttpStatus.OK
            );
        }
    }

    @RequestMapping( method = RequestMethod.PUT )
    public ResponseEntity updateApplication( @RequestBody @Validated Application application, BindingResult result ) {
        return ResponseBuilder.buildGET(
                conversionService.convert(
                        clientService.getClient( 1 ), IdApplication.class
                )
        );
    }

    @RequestMapping( value = "{appID}", method = RequestMethod.DELETE )
    public ResponseEntity deleteApplication( @PathVariable( "appID" ) int appID ) {
        return ResponseBuilder.buildGET(
                conversionService.convert(
                        clientService.getClient( appID ), IdApplication.class
                )
        );
    }

    @RequestMapping( value = "{appID}", method = RequestMethod.GET )
    public ResponseEntity getApplication( @PathVariable( "appID" ) int appID ) {
        return ResponseBuilder.buildGET(
                conversionService.convert(
                        clientService.getClient( appID ), IdApplication.class
                )
        );
    }

    @RequestMapping( value = "{appID}/secret", method = RequestMethod.POST )
    public ResponseEntity refreshApplicationSecret( @PathVariable( "appID" ) int appID ) {
        return ResponseBuilder.buildGET(
                conversionService.convert(
                        clientService.getClient( appID ), SecretIdApplication.class
                )
        );
    }

    @RequestMapping( value = "{appID}/token", method = RequestMethod.DELETE )
    public ResponseEntity revokeApplicationTokens( @PathVariable( "appID" ) int appID ) {
        return new ResponseEntity<>( "", HttpStatus.OK );
    }

}