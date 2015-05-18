package tw.edu.ncu.cc.oauth.server.concepts.apiToken

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject
import tw.edu.ncu.cc.oauth.server.concepts.security.SecretService

@Component
class ApiToken_ApiTokenObjectConverter implements Converter< ApiToken, ApiTokenObject >{

    @Autowired
    def SecretService secretService

    @Override
    ApiTokenObject convert( ApiToken source ) {
        ApiTokenObject apiTokenObject = new ApiTokenObject()
        apiTokenObject.last_updated = source.lastUpdated
        apiTokenObject.use_times = 0
        apiTokenObject.client_id = secretService.encodeHashId( source.client.id )
        apiTokenObject
    }

}
