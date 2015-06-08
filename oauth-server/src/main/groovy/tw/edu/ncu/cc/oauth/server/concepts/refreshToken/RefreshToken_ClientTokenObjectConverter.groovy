package tw.edu.ncu.cc.oauth.server.concepts.refreshToken

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClientObject
import tw.edu.ncu.cc.oauth.data.v1.management.token.ClientTokenObject
import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.concepts.client.Client_IdClientObjectConverter
import tw.edu.ncu.cc.oauth.server.helper.ScopeHelper

@Component
class RefreshToken_ClientTokenObjectConverter implements Converter< RefreshToken, ClientTokenObject >{

    @Autowired
    def Client_IdClientObjectConverter client_idClientObjectConverter

    @Override
    ClientTokenObject convert( RefreshToken source ) {

        ClientTokenObject clientTokenObject = new ClientTokenObject()
        clientTokenObject.id = source.id
        clientTokenObject.user = source.user.name
        clientTokenObject.client = convertFrom( source.client )
        clientTokenObject.scope = ScopeHelper.toStringArray( source.scope )
        clientTokenObject.last_updated = source.lastUpdated

        return clientTokenObject
    }

    private IdClientObject convertFrom( Client client ) {
        client == null ? null : client_idClientObjectConverter.convert( client )
    }

}

