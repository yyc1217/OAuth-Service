package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.data.v1.management.token.ClientAccessTokenObject
import tw.edu.ncu.cc.oauth.server.domain.AccessToken
import tw.edu.ncu.cc.oauth.server.service.domain.AccessTokenService

import static tw.edu.ncu.cc.oauth.server.helper.Responder.resource
import static tw.edu.ncu.cc.oauth.server.helper.Responder.respondWith

@RestController
@RequestMapping( value = "management/v1/access_token" )
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
                            accessTokenService.readAndUseUnexpiredByRealToken( token, [ 'user', 'scope' ] ), AccessTokenObject.class
                    );
                }
        )
    }

    @RequestMapping( value = "{id}", method = RequestMethod.GET )
    public ResponseEntity getTokenById( @PathVariable( "id" ) final String idOrToken ) {
        respondWith(
                resource()
                .pipe {
                    return conversionService.convert(
                            accessTokenService.readUnexpiredById( idOrToken, [ 'client', 'user', 'scope' ] ), ClientAccessTokenObject.class
                    );
                }
        )
    }

    @RequestMapping( value = "{id}", method = RequestMethod.DELETE )
    public ResponseEntity revokeTokenById( @PathVariable( "id" ) final String id ) {
        respondWith(
            resource()
            .pipe {
                accessTokenService.readUnexpiredById( id, [ 'client', 'user', 'scope' ] )
            }.pipe { AccessToken accessToken ->
                conversionService.convert(
                        accessTokenService.revoke( accessToken ), ClientAccessTokenObject.class
                );
            }
        )
    }

}