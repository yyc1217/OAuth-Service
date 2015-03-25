package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification

class ApiTokenServiceImplTest extends SpringSpecification {

    @Autowired
    ApiTokenService apiTokenService

    def "it can read client and use its api token with real api token 1"() {
        when:
            def useTimes1 =  apiTokenService.readAndUseByRealToken( "Mzo6OlRPS0VO" ).apiUseTimes
            def useTimes2 =  apiTokenService.readAndUseByRealToken( "Mzo6OlRPS0VO" ).apiUseTimes
        then:
            useTimes1 != useTimes2
    }

    def "it can read client and use its api token with real api token 2"() {
        expect:
            apiTokenService.readAndUseByRealToken( "NOT EXIST" ) == null
    }

}
