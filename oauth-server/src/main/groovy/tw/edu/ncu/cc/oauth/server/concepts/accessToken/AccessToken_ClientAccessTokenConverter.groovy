package tw.edu.ncu.cc.oauth.server.concepts.accessToken

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClientObject
import tw.edu.ncu.cc.oauth.data.v1.management.token.ClientAccessTokenObject
import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.concepts.client.Client_IdClientObjectConverter
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
        clientAccessTokenObject.client = convertFrom( source.client )
        clientAccessTokenObject.scope = ScopeHelper.toStringArray( source.scope )
        clientAccessTokenObject.last_updated = source.lastUpdated

        return clientAccessTokenObject
    }

    private IdClientObject convertFrom( Client client ) {
        client == null ? null : client_idClientObjectConverter.convert( client )
    }

}

