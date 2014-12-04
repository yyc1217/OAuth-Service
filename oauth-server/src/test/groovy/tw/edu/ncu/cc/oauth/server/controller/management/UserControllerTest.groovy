package tw.edu.ncu.cc.oauth.server.controller.management

import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class UserControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/user"

    def "it can handle get of specified AccessToken 1"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/ADMIN1/token" )
                    ).andExpect( status().isOk() ).andReturn()
            )
        then:
            response[0].user == "ADMIN1"
    }

    def "it can handle get of specified AccessToken 2"() {
        expect:
            server().perform(
                    get( targetURL + "/NOT_EXIST/token" )
            ).andExpect( status().isNotFound() )
    }

}
