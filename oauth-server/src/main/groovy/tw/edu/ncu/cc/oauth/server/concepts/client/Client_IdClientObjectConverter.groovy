package tw.edu.ncu.cc.oauth.server.concepts.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClientObject
import tw.edu.ncu.cc.oauth.server.concepts.security.SecretService

@Component
class Client_IdClientObjectConverter implements Converter< Client, IdClientObject >{

    @Autowired
    def SecretService secretService

    @Override
    IdClientObject convert( Client source ) {
        IdClientObject idClientObject = new IdClientObject()
        idClientObject.id = secretService.encodeHashId( source.id )
        idClientObject.name = source.name
        idClientObject.description = source.description
        idClientObject.owner = source.owner.name
        idClientObject.url = source.url
        idClientObject.callback = source.callback
        return idClientObject
    }

}
