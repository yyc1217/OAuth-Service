package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject
import tw.edu.ncu.cc.oauth.server.concepts.apiToken.ApiTokenService

import static tw.edu.ncu.cc.oauth.server.helper.Responder.resource
import static tw.edu.ncu.cc.oauth.server.helper.Responder.respondWith

@RestController
@RequestMapping( value = "management/v1/api_tokens" )
class APITokenController {

    @Autowired
    def ConversionService conversionService

    @Autowired
    def ApiTokenService apiTokenService

    @RequestMapping( value = "token/{token}", method = RequestMethod.GET )
    public ResponseEntity getTokenByToken( @PathVariable( "token" ) final String token ) {
        respondWith(
                resource()
                .pipe {
                    conversionService.convert(
                            apiTokenService.findUnexpiredByToken( token ), ApiTokenObject.class
                    )
                }
        )
    }

}
