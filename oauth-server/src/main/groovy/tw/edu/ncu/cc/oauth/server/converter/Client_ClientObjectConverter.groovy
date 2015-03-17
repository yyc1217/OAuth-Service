package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.client.ClientObject
import tw.edu.ncu.cc.oauth.server.domain.Client

@Component
class Client_ClientObjectConverter implements Converter< Client, ClientObject >{

    @Override
    ClientObject convert( Client source ) {
        ClientObject clientObject = new ClientObject()
        clientObject.name = source.name
        clientObject.description = source.description
        clientObject.owner = source.owner.name
        clientObject.url = source.url
        clientObject.callback = source.callback
        return clientObject
    }

}
