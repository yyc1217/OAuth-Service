package tw.edu.ncu.cc.oauth.server.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tw.edu.ncu.cc.oauth.data.v1.management.application.Application;
import tw.edu.ncu.cc.oauth.data.v1.management.application.IdApplication;
import tw.edu.ncu.cc.oauth.data.v1.management.application.SecretIdApplication;
import tw.edu.ncu.cc.oauth.server.exception.handler.APIExceptionHandler;
import tw.edu.ncu.cc.oauth.server.helper.ResponseBuilder;
import tw.edu.ncu.cc.oauth.server.service.ClientService;
import tw.edu.ncu.cc.oauth.server.validator.ApplicationValidator;

@RestController
@RequestMapping( value = "management/v1/application" )
public class ApplicationController extends APIExceptionHandler {

    private ClientService clientService;
    private ConversionService conversionService;

    @Autowired
    public void setClientAPIService( ClientService clientService ) {
        this.clientService = clientService;
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
    public ResponseEntity createApplication( @RequestBody @Validated final Application application, BindingResult result ) {
        return ResponseBuilder
                .validation()
                .errors( result )
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                clientService.createClient( application ), SecretIdApplication.class
                        );
                    }
                } )
                .build();
    }

    @RequestMapping( value = "{appID}", method = RequestMethod.GET )
    public ResponseEntity getApplication( @PathVariable( "appID" ) final String appID ) {
        return ResponseBuilder
                .noneValidation()
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                clientService.readClientByID( appID ), IdApplication.class
                        );
                    }
                } )
                .build();
    }

    @RequestMapping( value = "{appID}", method = RequestMethod.PUT )
    public ResponseEntity updateApplication( @PathVariable( "appID" ) final String appID,
                                             @RequestBody @Validated  final Application application, final BindingResult result ) {
        return ResponseBuilder
                .validation()
                .errors( result )
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                clientService.updateClient( appID, application ), IdApplication.class
                        );
                    }
                } )
                .build();
    }

    @RequestMapping( value = "{appID}", method = RequestMethod.DELETE )
    public ResponseEntity deleteApplication( @PathVariable( "appID" ) final String appID ) {
        return ResponseBuilder
                .noneValidation()
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                clientService.deleteClientByID( appID ), IdApplication.class
                        );
                    }
                } )
                .build();
    }

    @RequestMapping( value = "{appID}/secret", method = RequestMethod.POST )
    public ResponseEntity refreshApplicationSecret( @PathVariable( "appID" ) final String appID ) {
        return ResponseBuilder
                .noneValidation()
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                clientService.refreshClientSecret( appID ), SecretIdApplication.class
                        );
                    }
                } )
                .build();
    }

}