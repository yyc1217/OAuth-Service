package tw.edu.ncu.cc.oauth.server.concepts.accessToken

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.server.concepts.security.SecretService
import tw.edu.ncu.cc.oauth.server.helper.ScopeHelper

@Component
class AccessToken_AccessTokenObjectConverter implements Converter< AccessToken, AccessTokenObject > {

    @Autowired
    def SecretService secretService

    @Override
    AccessTokenObject convert( AccessToken source ) {
        AccessTokenObject accessTokenObject = new AccessTokenObject()
        accessTokenObject.id = source.id
        accessTokenObject.client_id = secretService.encodeHashId( source.client.id )
        accessTokenObject.user = source.user.name
        accessTokenObject.scope = ScopeHelper.toStringArray( source.scope )
        accessTokenObject.last_updated = source.lastUpdated
        return accessTokenObject
    }

}
