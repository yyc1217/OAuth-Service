package tw.edu.ncu.cc.oauth.server.controller.management

import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class TokenControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/token"

    def "it can handle get of specified AccessToken"() {
        when:
            def response = JSON(
                server().perform(
                        get( targetURL + "/1" )
                ).andExpect( status().isOk() ).andReturn()
            )
        then:
            response.user == "ADMIN1"
    }

    //TODO TEST DELETE ACTION

}
