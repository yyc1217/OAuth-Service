package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.TypeDescriptor
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import tw.edu.ncu.cc.oauth.data.v1.management.user.ManagerCreateObject
import tw.edu.ncu.cc.oauth.data.v1.management.user.ManagerObject
import tw.edu.ncu.cc.oauth.server.concepts.manager.ManagerCreateValidator
import tw.edu.ncu.cc.oauth.server.concepts.manager.ManagerService
import tw.edu.ncu.cc.oauth.server.concepts.user.User
import tw.edu.ncu.cc.oauth.server.concepts.user.UserService

import static tw.edu.ncu.cc.oauth.server.helper.Responder.resource
import static tw.edu.ncu.cc.oauth.server.helper.Responder.respondWith

@RestController
@RequestMapping( value = "management/v1/managers" )
public class ManagerController {

    @Autowired
    def UserService userService

    @Autowired
    def ManagerService managerService

    @Autowired
    def ConversionService conversionService

    @InitBinder
    public static void initBinder( WebDataBinder binder ) {
        binder.addValidators( new ManagerCreateValidator() );
    }

    @RequestMapping(  method = RequestMethod.GET )
    public ResponseEntity index( Pageable pageable ) {
        respondWith(
                resource()
                .pipe {
                    conversionService.convert(
                            managerService.findAllManagers( pageable ),
                            TypeDescriptor.collection( List.class, TypeDescriptor.valueOf( User.class ) ),
                            TypeDescriptor.array( TypeDescriptor.valueOf( ManagerObject.class ) )
                    )
                }
        )
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity create( @Validated @RequestBody final ManagerCreateObject managerObject, BindingResult validation ) {
        respondWith(
                resource()
                .validate( validation )
                .pipe {
                    User user = userService.findByName( managerObject.id )
                    if( user == null ) {
                        managerService.create( new User( name: managerObject.id ) )
                    } else {
                        managerService.create( user )
                    }
                }
        )
    }

    @RequestMapping( value = "{userid}", method = RequestMethod.GET )
    public ResponseEntity read( @PathVariable( "userid" ) String username ) {
        respondWith(
                resource()
                .pipe {
                    conversionService.convert(
                            managerService.findByName( username ) , ManagerObject.class
                    );
                }
        )
    }

    @RequestMapping( value = "{userid}", method = RequestMethod.DELETE )
    public ResponseEntity delete(  @PathVariable( "userid" ) String username ) {
        respondWith(
                resource()
                .pipe {
                    managerService.findByName( username )
                }
                .pipe { User user ->
                    conversionService.convert(
                            managerService.delete( user ) , ManagerObject.class
                    );
                }
        )
    }

}