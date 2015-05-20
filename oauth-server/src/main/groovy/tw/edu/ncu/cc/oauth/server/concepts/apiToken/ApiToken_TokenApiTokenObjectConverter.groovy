package tw.edu.ncu.cc.oauth.server.concepts.apiToken

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.token.TokenApiTokenObject
import tw.edu.ncu.cc.oauth.server.concepts.security.SecretService

@Component
class ApiToken_TokenApiTokenObjectConverter implements Converter< ApiToken, TokenApiTokenObject >{

    @Autowired
    def SecretService secretService

    @Override
    TokenApiTokenObject convert( ApiToken source ) {
        TokenApiTokenObject apiTokenObject = new TokenApiTokenObject()
        apiTokenObject.id = source.id
        apiTokenObject.token = source.token
        apiTokenObject.client_id = secretService.encodeHashId( source.client.id )
        apiTokenObject
    }

}
