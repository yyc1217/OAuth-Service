package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject
import tw.edu.ncu.cc.oauth.server.domain.Client


@Component
class Client_ApiTokenObjectConverter implements Converter< Client, ApiTokenObject >{

    @Override
    ApiTokenObject convert( Client source ) {
        ApiTokenObject apiTokenObject = new ApiTokenObject()
        apiTokenObject.last_updated = source.lastUpdated
        apiTokenObject.use_times = source.apiUseTimes
        return apiTokenObject
    }

}
