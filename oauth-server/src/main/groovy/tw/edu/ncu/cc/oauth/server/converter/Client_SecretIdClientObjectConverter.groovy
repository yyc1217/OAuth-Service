package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.client.SecretIdClientObject
import tw.edu.ncu.cc.oauth.server.domain.Client

@Component
class Client_SecretIdClientObjectConverter implements Converter< Client, SecretIdClientObject >{

    @Override
    SecretIdClientObject convert( Client source ) {
        SecretIdClientObject secretIdClientObject = new SecretIdClientObject()
        secretIdClientObject.id = source.id
        secretIdClientObject.secret = source.secret
        secretIdClientObject.name = source.name
        secretIdClientObject.description = source.description
        secretIdClientObject.owner = source.owner.name
        secretIdClientObject.url = source.url
        secretIdClientObject.callback = source.callback
        return secretIdClientObject
    }

}
