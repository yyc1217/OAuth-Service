package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.data.v1.management.token.ClientAccessTokenObject
import tw.edu.ncu.cc.oauth.server.domain.AccessToken
import tw.edu.ncu.cc.oauth.server.helper.ResponseBuilder
import tw.edu.ncu.cc.oauth.server.service.domain.AccessTokenService

@RestController
@RequestMapping( value = "management/v1/token" )
public class TokenController {

    @Autowired
    def ConversionService conversionService;

    @Autowired
    def AccessTokenService accessTokenService;

    @RequestMapping( value = "{id}", method = RequestMethod.GET )
    public ResponseEntity getTokenById( @PathVariable( "id" ) final String id ) {
        return ResponseBuilder
                .noneValidation()
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                accessTokenService.readUnexpiredById( id, [ 'client', 'user', 'scope' ] ), ClientAccessTokenObject.class
                        );
                    }
                } )
                .build();
    }

    @RequestMapping( value = "{id}", method = RequestMethod.DELETE )
    public ResponseEntity deleteTokenById( @PathVariable( "id" ) final String id ) {
        return ResponseBuilder
                .noneValidation()
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        AccessToken accessToken = accessTokenService.readUnexpiredById( id, [ 'client', 'user', 'scope' ] )
                        if( accessToken == null ) {
                            return null
                        } else {
                            return conversionService.convert(
                                    accessTokenService.revoke( accessToken ), ClientAccessTokenObject.class
                            );
                        }
                    }
                } )
                .build();
    }

    @RequestMapping( value = "string/{token}", method = RequestMethod.GET )
    public ResponseEntity getTokenByString( @PathVariable( "token" ) final String token ) {
        return ResponseBuilder
                .noneValidation()
                .resource( new ResponseBuilder.ResourceBuilder() {
                    @Override
                    public Object build() {
                        return conversionService.convert(
                                accessTokenService.readUnexpiredByRealToken( token, [ 'user', 'scope' ] ), AccessTokenObject.class
                        );
                    }
                } )
                .build();
    }

}