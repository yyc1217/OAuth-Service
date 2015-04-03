package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.domain.ApiToken
import tw.edu.ncu.cc.oauth.server.domain.Client

class ApiTokenServiceImplTest extends SpringSpecification {

    @Autowired
    ApiTokenService apiTokenService

    def "it can read client and use its api token with real api token 1"() {
        when:
            def useTimes1 =  apiTokenService.readAndUseByRealToken( "Mzo6OlRPS0VO" ).client.apiUseTimes
            def useTimes2 =  apiTokenService.readAndUseByRealToken( "Mzo6OlRPS0VO" ).client.apiUseTimes
        then:
            useTimes1 != useTimes2
    }

    def "it can read client and use its api token with real api token 2"() {
        expect:
            apiTokenService.readAndUseByRealToken( "NOT EXIST" ) == null
    }

    def "it can create api token"() {
        when:
            def response = apiTokenService.create( new ApiToken(
                    client: Client.get( 1 ),
                    dateExpired: laterTime()
            ) )
        and:
            def apiToken = apiTokenService.readAndUseByRealToken( response.token )
        then:
            apiToken.client.name == 'APP1'
    }

}
