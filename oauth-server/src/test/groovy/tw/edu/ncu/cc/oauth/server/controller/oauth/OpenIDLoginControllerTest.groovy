package tw.edu.ncu.cc.oauth.server.controller.oauth

import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class OpenIDLoginControllerTest extends IntegrationSpecification {

    def "it should redirect login path to remote address"() {
        expect:
            server().perform(
                    get( "/login_page" )
            ).andExpect(
                    status().isFound()
            )
    }

}
