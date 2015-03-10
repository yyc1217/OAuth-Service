package tw.edu.ncu.cc.oauth.server.web.oauth

import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class OpenIDLoginControllerTest extends IntegrationSpecification {

    def "it should redirect login path to remote address"() {
        expect:
            server().perform(
                    get( "/login" )
            ).andExpect(
                    status().isFound()
            )
    }

}
