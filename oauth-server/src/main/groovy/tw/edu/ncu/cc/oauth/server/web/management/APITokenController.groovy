package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject
import tw.edu.ncu.cc.oauth.server.service.domain.ApiTokenService

import static tw.edu.ncu.cc.oauth.server.helper.Responder.resource
import static tw.edu.ncu.cc.oauth.server.helper.Responder.respondWith

@RestController
@RequestMapping( value = "management/v1/api_tokens" )
class APITokenController {

    @Autowired
    def ConversionService conversionService

    @Autowired
    def ApiTokenService apiTokenService

    @Value( '${custom.api.limit-times}' )
    def long api_limit_times

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity getTokenByToken( @RequestHeader( "token" ) final String token ) {
        respondWith(
                resource()
                .pipe {
                    ApiTokenObject apiTokenObject = conversionService.convert(
                            apiTokenService.readAndUseByRealToken( token ), ApiTokenObject.class
                    )
                    if( apiTokenObject != null && apiTokenObject.use_times > api_limit_times ) {
                        return new ResponseEntity<>( "reach limit:" + api_limit_times, HttpStatus.FORBIDDEN )
                    } else {
                        return apiTokenObject
                    }
                }
        )
    }

}
