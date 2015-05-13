package tw.edu.ncu.cc.oauth.server.concepts.apiToken

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject

@Component
class ApiToken_ApiTokenObjectConverter implements Converter< ApiToken, ApiTokenObject >{

    @Override
    ApiTokenObject convert( ApiToken source ) {
        ApiTokenObject apiTokenObject = new ApiTokenObject()
        apiTokenObject.last_updated = source.lastUpdated
        return apiTokenObject
    }

}
