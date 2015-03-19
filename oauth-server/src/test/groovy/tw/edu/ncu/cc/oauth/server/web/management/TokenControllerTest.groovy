package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.transaction.annotation.Transactional
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TokenControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/token"

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
                            get( targetURL + "/string/Mzo6OlRPS0VO" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.user == "ADMIN3"
    }

    @Transactional
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