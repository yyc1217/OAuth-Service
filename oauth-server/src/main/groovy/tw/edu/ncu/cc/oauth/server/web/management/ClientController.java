package tw.edu.ncu.cc.oauth.server.web.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tw.edu.ncu.cc.oauth.data.v1.management.client.Client;
import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClient;
import tw.edu.ncu.cc.oauth.data.v1.management.client.SecretIdClient;
import tw.edu.ncu.cc.oauth.server.helper.ResponseBuilder;
import tw.edu.ncu.cc.oauth.server.service.ClientService;
import tw.edu.ncu.cc.oauth.server.validator.ClientValidator;

@RestController
@RequestMapping( value = "management/v1/application" )
public class ClientController {

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
        binder.addValidators( new ClientValidator() );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity createApplication( @RequestBody @Validated final Client client, BindingResult result ) {
        return ResponseBuilder
                .validation()
                .errors( result )
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                clientService.createClient( client ), SecretIdClient.class
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
                                clientService.readClientByID( appID ), IdClient.class
                        );
                    }
                } )
                .build();
    }

    @RequestMapping( value = "{appID}", method = RequestMethod.PUT )
    public ResponseEntity updateApplication( @PathVariable( "appID" ) final String appID,
                                             @RequestBody @Validated  final Client client, final BindingResult result ) {
        return ResponseBuilder
                .validation()
                .errors( result )
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                clientService.updateClient( appID, client ), IdClient.class
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
                                clientService.deleteClientByID( appID ), IdClient.class
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
                                clientService.refreshClientSecret( appID ), SecretIdClient.class
                        );
                    }
                } )
                .build();
    }

}