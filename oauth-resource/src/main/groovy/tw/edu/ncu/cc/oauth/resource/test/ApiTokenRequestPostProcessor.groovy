package tw.edu.ncu.cc.oauth.resource.test

import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.web.servlet.request.RequestPostProcessor
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject
import tw.edu.ncu.cc.oauth.resource.config.RequestConfig
import tw.edu.ncu.cc.oauth.resource.core.ApiCredentialHolder


class ApiTokenRequestPostProcessor implements RequestPostProcessor {

    private int apiUseTimes
    private String apiToken
    private String clientId

    ApiTokenRequestPostProcessor( String apiToken ) {
        this.apiToken = apiToken
    }

    ApiTokenRequestPostProcessor apiUseTimes( int apiUseTimes ) {
        this.apiUseTimes = apiUseTimes
        this
    }

    ApiTokenRequestPostProcessor clientId( String clientId ) {
        this.clientId = clientId
        this
    }

    @Override
    MockHttpServletRequest postProcessRequest( MockHttpServletRequest request ) {
        request.addHeader( RequestConfig.API_TOKEN_HEADER, apiToken )
        ApiCredentialHolder.addApiToken( apiToken, new ApiTokenObject(
                use_times: apiUseTimes,
                last_updated: new Date(),
                client_id: clientId
        ) )
        request
    }

}
