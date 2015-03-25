package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.test.annotation.Rollback
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AccessTokenControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/access_token"

    def "user can get access token info by id"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/1" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.user == "ADMIN1"
    }

    def "user can get access token info by token"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/Mzo6OlRPS0VO" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.user == "ADMIN3"
    }

    @Rollback
    def "user can delete access token by id"() {
        when:
            server().perform(
                    delete( targetURL + "/1" )
            ).andExpect(
                    status().isOk()
            )
        then:
            server().perform(
                    get( targetURL + "/1" )
            ).andExpect(
                    status().isNotFound()
            )
    }

}