package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import tw.edu.ncu.cc.oauth.data.v1.management.client.ClientObject
import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClientObject
import tw.edu.ncu.cc.oauth.data.v1.management.client.SecretIdClientObject
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.User
import tw.edu.ncu.cc.oauth.server.helper.ResponseBuilder
import tw.edu.ncu.cc.oauth.server.service.domain.ClientService
import tw.edu.ncu.cc.oauth.server.validator.ClientValidator

@RestController
@RequestMapping( value = "management/v1/application" )
public class ClientController {

    @Autowired
    def ClientService clientService;

    @Autowired
    def ConversionService conversionService;

    @InitBinder
    public static void initBinder( WebDataBinder binder ) {
        binder.addValidators( new ClientValidator() );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity createApplication( @RequestBody @Validated final ClientObject clientObject, BindingResult result ) {
        return ResponseBuilder
                .validation()
                .errors( result )
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                clientService.create( new Client(
                                        name: clientObject.name,
                                        description: clientObject.description,
                                        url: clientObject.url,
                                        callback: clientObject.callback,
                                        owner: User.findByName( clientObject.owner )
                                ) ), SecretIdClientObject.class
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
                                clientService.readByID( appID, [ owner: 'join' ] ), IdClientObject.class
                        );
                    }
                } )
                .build();
    }

    @RequestMapping( value = "{appID}", method = RequestMethod.PUT )
    public ResponseEntity updateApplication( @PathVariable( "appID" ) final String appID,
                                             @RequestBody @Validated  final ClientObject clientObject, final BindingResult result ) {
        return ResponseBuilder
                .validation()
                .errors( result )
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        Client client = clientService.readByID( appID, [ owner: 'join' ] )
                        if( client == null ) {
                            return null
                        } else {
                            client.name = clientObject.name
                            client.url = clientObject.url
                            client.callback = clientObject.callback
                            client.description = clientObject.description

                            return conversionService.convert(
                                    clientService.update( client ), IdClientObject.class
                            );
                        }
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
                        Client client =  clientService.readByID( appID, [ owner: 'join' ] )
                        if( client == null ) {
                            return null
                        } else {
                            return conversionService.convert(
                                    clientService.delete( client ), IdClientObject.class
                            );
                        }
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
                        Client client =  clientService.readByID( appID, [ owner: 'join' ] )
                        if( client == null ) {
                            return null
                        } else {
                            return conversionService.convert(
                                    clientService.refreshSecret( client ), SecretIdClientObject.class
                            );
                        }
                    }
                } )
                .build();
    }

}