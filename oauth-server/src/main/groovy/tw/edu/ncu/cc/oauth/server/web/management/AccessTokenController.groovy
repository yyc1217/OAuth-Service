package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.data.v1.management.token.ClientAccessTokenObject
import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessToken
import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessTokenService
import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessToken_

import static tw.edu.ncu.cc.oauth.server.helper.Responder.resource
import static tw.edu.ncu.cc.oauth.server.helper.Responder.respondWith

@RestController
@RequestMapping( value = "management/v1/access_tokens" )
public class AccessTokenController {

    @Autowired
    def ConversionService conversionService;

    @Autowired
    def AccessTokenService accessTokenService;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity getTokenByToken( @RequestHeader( "token" ) final String token ) {
        respondWith(
                resource()
                .pipe {
                    conversionService.convert(
                            accessTokenService.findUnexpiredByToken( token, AccessToken_.scope ), AccessTokenObject.class
                    );
                }
        )
    }

    @RequestMapping( value = "{id}", method = RequestMethod.GET )
    public ResponseEntity getTokenById( @PathVariable( "id" ) final String id ) {
        respondWith(
                resource()
                .pipe {
                    return conversionService.convert(
                            accessTokenService.findUnexpiredById( id, AccessToken_.scope ), ClientAccessTokenObject.class
                    );
                }
        )
    }

    @RequestMapping( value = "{id}", method = RequestMethod.DELETE )
    public ResponseEntity revokeTokenById( @PathVariable( "id" ) final String id ) {
        respondWith(
            resource()
            .pipe {
                accessTokenService.findUnexpiredById( id, AccessToken_.scope )
            }.pipe { AccessToken accessToken ->
                conversionService.convert(
                        accessTokenService.revoke( accessToken ), ClientAccessTokenObject.class
                );
            }
        )
    }

}