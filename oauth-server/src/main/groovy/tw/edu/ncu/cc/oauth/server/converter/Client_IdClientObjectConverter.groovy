package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClientObject
import tw.edu.ncu.cc.oauth.server.domain.Client

@Component
class Client_IdClientObjectConverter implements Converter< Client, IdClientObject >{

    @Override
    IdClientObject convert( Client source ) {
        IdClientObject idClientObject = new IdClientObject()
        idClientObject.id = source.id
        idClientObject.name = source.name
        idClientObject.description = source.description
        idClientObject.owner = source.owner.name
        idClientObject.url = source.url
        idClientObject.callback = source.callback
        return idClientObject
    }

}
