package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.TypeDescriptor
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import tw.edu.ncu.cc.oauth.data.v1.management.client.ClientObject
import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClientObject
import tw.edu.ncu.cc.oauth.data.v1.management.client.SecretIdClientObject
import tw.edu.ncu.cc.oauth.data.v1.management.token.TokenApiTokenObject
import tw.edu.ncu.cc.oauth.server.concepts.apiToken.ApiToken
import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.concepts.client.ClientService
import tw.edu.ncu.cc.oauth.server.concepts.client.ClientValidator
import tw.edu.ncu.cc.oauth.server.concepts.client.Client_
import tw.edu.ncu.cc.oauth.server.concepts.user.UserService

import static tw.edu.ncu.cc.oauth.server.helper.Responder.resource
import static tw.edu.ncu.cc.oauth.server.helper.Responder.respondWith

@RestController
@RequestMapping( value = "management/v1/clients" )
public class ClientController {

    @Autowired
    def UserService userService

    @Autowired
    def ClientService clientService;

    @Autowired
    def ConversionService conversionService;

    @InitBinder
    public static void initBinder( WebDataBinder binder ) {
        binder.addValidators( new ClientValidator() );
    }

    /**
     * ·j´Mclients
     * <code>/clients?name={clientName}&id={clientId}&owner={portalId}&isDeleted={true or false}</code>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam(value = "id", required = false) String id,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "owner", required = false) String owner,
                                 @RequestParam(value = "isDeleted", defaultValue = "false") Boolean isDeleted) {

        def dto = IdClientObject.newInstance(
                id : id,
                name : name,
                owner : owner,
                isDeleted : isDeleted
        )

        respondWith(
            resource()
            .pipe {
                clientService.findByDTO(dto)
            }.pipe { List<Client> clients ->
                conversionService.convert(
                        clients,
                        TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(Client.class)),
                        TypeDescriptor.array(TypeDescriptor.valueOf(ClientObject.class))
                );
            }
        )
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity create( @RequestBody @Validated final ClientObject clientObject, BindingResult validation ) {
        respondWith(
            resource()
            .validate( validation )
            .pipe {
                conversionService.convert(
                        clientService.create( new Client(
                                name: clientObject.name,
                                description: clientObject.description,
                                url: clientObject.url,
                                callback: clientObject.callback,
                                owner: userService.findByName( clientObject.owner )
                        ) ), SecretIdClientObject.class
                );
            }
        )
    }

    @RequestMapping( value = "{id}", method = RequestMethod.GET )
    public ResponseEntity get( @PathVariable( "id" ) final String clientId ) {
        respondWith(
                resource()
                .pipe {
                    conversionService.convert(
                            clientService.findUndeletedBySerialId( clientId ), SecretIdClientObject.class
                    );
                }
        )
    }

    @RequestMapping( value = "{id}", method = RequestMethod.PUT )
    public ResponseEntity update( @PathVariable( "id" ) final String clientId,
                                  @RequestBody @Validated  final ClientObject clientObject, final BindingResult validation ) {
        respondWith(
            resource()
            .validate( validation )
            .pipe {
                clientService.findUndeletedBySerialId( clientId )
            }.pipe { Client client ->
                client.name = clientObject.name
                client.url = clientObject.url
                client.callback = clientObject.callback
                client.description = clientObject.description

                conversionService.convert(
                        clientService.update( client ), SecretIdClientObject.class
                );
            }
        )
    }

    @RequestMapping( value = "{id}", method = RequestMethod.DELETE )
    public ResponseEntity delete( @PathVariable( "id" ) final String clientId ) {
        respondWith(
            resource()
            .pipe {
                clientService.findUndeletedBySerialId( clientId )
            }.pipe { Client client ->
                conversionService.convert(
                        clientService.delete( client ), SecretIdClientObject.class
                );
            }
        )
    }

    @RequestMapping( value = "{id}/refresh_secret", method = RequestMethod.POST )
    public ResponseEntity refresh( @PathVariable( "id" ) final String clientId ) {
        respondWith(
            resource()
            .pipe {
                clientService.findUndeletedBySerialId( clientId )
            }.pipe { Client client ->
                conversionService.convert(
                        clientService.refreshSecret( client ), SecretIdClientObject.class
                );
            }
        )
    }

    @RequestMapping( value = "{id}/api_tokens", method = RequestMethod.GET )
    public ResponseEntity getApiTokens( @PathVariable( "id" ) final String clientId ) {
        respondWith(
                resource()
                .pipe {
                    clientService.findUndeletedBySerialId( clientId, Client_.apiTokens )
                }.pipe { Client client ->
                    conversionService.convert(
                            client.apiTokens,
                            TypeDescriptor.collection( Set.class, TypeDescriptor.valueOf( ApiToken.class ) ),
                            TypeDescriptor.array( TypeDescriptor.valueOf( TokenApiTokenObject.class ) )
                    );
                }
        )
    }

}