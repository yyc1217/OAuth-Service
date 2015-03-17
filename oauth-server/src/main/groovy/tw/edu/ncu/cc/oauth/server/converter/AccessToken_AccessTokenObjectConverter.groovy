package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.server.domain.AccessToken
import tw.edu.ncu.cc.oauth.server.helper.ScopeHelper

@Component
class AccessToken_AccessTokenObjectConverter implements Converter< AccessToken, AccessTokenObject > {

    @Override
    AccessTokenObject convert( AccessToken source ) {
        AccessTokenObject accessTokenObject = new AccessTokenObject()
        accessTokenObject.id = source.id
        accessTokenObject.user = source.user.name
        accessTokenObject.scope = ScopeHelper.toStringArray( source.scope )
        accessTokenObject.last_updated = source.lastUpdated
        return accessTokenObject
    }

}
