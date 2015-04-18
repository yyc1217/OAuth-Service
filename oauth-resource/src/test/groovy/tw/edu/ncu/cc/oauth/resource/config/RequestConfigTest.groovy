package tw.edu.ncu.cc.oauth.resource.config

import spock.lang.Specification


class RequestConfigTest extends Specification {

    def "it has embedded values"() {
        expect:
            RequestConfig.ACCESS_TOKEN_PREFIX == "Bearer"
            RequestConfig.ACCESS_TOKEN_HEADER == "Authorization"
            RequestConfig.API_TOKEN_HEADER   == "X-NCU-API-TOKEN"
    }

}
