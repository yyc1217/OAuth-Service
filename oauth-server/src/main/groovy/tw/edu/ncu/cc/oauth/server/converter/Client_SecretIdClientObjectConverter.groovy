package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.client.SecretIdClientObject
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.service.security.SecretService

@Component
class Client_SecretIdClientObjectConverter implements Converter< Client, SecretIdClientObject >{

    @Autowired
    def SecretService secretService

    @Override
    SecretIdClientObject convert( Client source ) {
        SecretIdClientObject secretIdClientObject = new SecretIdClientObject()
        secretIdClientObject.id = secretService.encodeHashId( source.id )
        secretIdClientObject.secret = source.secret
        secretIdClientObject.apiToken = source.apiToken
        secretIdClientObject.name = source.name
        secretIdClientObject.description = source.description
        secretIdClientObject.owner = source.owner.name
        secretIdClientObject.url = source.url
        secretIdClientObject.callback = source.callback
        return secretIdClientObject
    }

}
