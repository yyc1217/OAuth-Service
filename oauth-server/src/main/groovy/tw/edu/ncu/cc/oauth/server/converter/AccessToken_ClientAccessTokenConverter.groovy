package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.token.ClientAccessTokenObject
import tw.edu.ncu.cc.oauth.server.domain.AccessToken
import tw.edu.ncu.cc.oauth.server.helper.ScopeHelper

@Component
class AccessToken_ClientAccessTokenConverter implements Converter< AccessToken, ClientAccessTokenObject >{

    @Autowired
    def Client_IdClientObjectConverter client_idClientObjectConverter

    @Override
    ClientAccessTokenObject convert( AccessToken source ) {

        ClientAccessTokenObject clientAccessTokenObject = new ClientAccessTokenObject()
        clientAccessTokenObject.id = source.id
        clientAccessTokenObject.user = source.user.name
        clientAccessTokenObject.client = client_idClientObjectConverter.convert( source.client )
        clientAccessTokenObject.scope = ScopeHelper.toStringArray( source.scope )
        clientAccessTokenObject.last_updated = source.lastUpdated

        return clientAccessTokenObject
    }

}

