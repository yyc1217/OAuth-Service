package tw.edu.ncu.cc.oauth.server.web.management

import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AccessTokenControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/access_tokens"

    def "user can get access token info by token"() {
        given:
            def accessToken = a_accessToken()
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/token/" + accessToken.token )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.user == accessToken.user.name
    }

}