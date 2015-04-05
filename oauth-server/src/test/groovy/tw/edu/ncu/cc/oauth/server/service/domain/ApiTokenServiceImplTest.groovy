package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification

class ApiTokenServiceImplTest extends SpringSpecification {

    @Autowired
    ApiTokenService apiTokenService

    def "it can read client and use its api token with real api token 1"() {
        given:
            def apiToken = a_apiToken()
        when:
            def useTimes1 =  apiTokenService.readAndUseByRealToken( apiToken.token ).client.apiUseTimes
            def useTimes2 =  apiTokenService.readAndUseByRealToken( apiToken.token ).client.apiUseTimes
        then:
            useTimes1 != useTimes2
    }

    def "it can read client and use its api token with real api token 2"() {
        expect:
            apiTokenService.readAndUseByRealToken( "NOT EXIST" ) == null
    }

    def "it can create api token"() {
        given:
            def apiToken = new_apiToken()
        when:
            def createdApiToken = apiTokenService.create( apiToken )
        and:
            def managedApiToken = apiTokenService.readAndUseByRealToken( createdApiToken.token )
        then:
            managedApiToken.client.name == apiToken.client.name
    }

}
