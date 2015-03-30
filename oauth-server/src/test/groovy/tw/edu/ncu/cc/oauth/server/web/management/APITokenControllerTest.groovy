package tw.edu.ncu.cc.oauth.server.web.management

import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class APITokenControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/api_token"

    def "user can get api token info by token"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/Mzo6OlRPS0VO" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.use_times >= 0
    }

}
