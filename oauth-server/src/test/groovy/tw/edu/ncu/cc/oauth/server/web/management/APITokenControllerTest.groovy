package tw.edu.ncu.cc.oauth.server.web.management

import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class APITokenControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/api_token"

    def "user can get api token info by token"() {
        given:
            def apiToken = a_apiToken()
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL ) .header( "token", apiToken. token )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.use_times >= 0
    }

}
