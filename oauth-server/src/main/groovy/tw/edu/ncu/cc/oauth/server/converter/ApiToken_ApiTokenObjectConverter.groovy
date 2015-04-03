package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject
import tw.edu.ncu.cc.oauth.server.domain.ApiToken

@Component
class ApiToken_ApiTokenObjectConverter implements Converter< ApiToken, ApiTokenObject >{

    @Override
    ApiTokenObject convert( ApiToken source ) {
        ApiTokenObject apiTokenObject = new ApiTokenObject()
        apiTokenObject.last_updated = source.lastUpdated
        apiTokenObject.use_times = source.client.apiUseTimes
        return apiTokenObject
    }

}
