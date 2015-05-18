package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.transaction.annotation.Transactional
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AccessTokenControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/access_tokens"

    def "user can get access token info by id"() {
        given:
            def accessToken = a_accessToken()
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/" + accessToken.id )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.user == accessToken.user.name
    }

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

    @Transactional
    def "user can delete access token by id"() {
        when:
            server().perform(
                    delete( targetURL + "/3" )
            ).andExpect(
                    status().isOk()
            )
        then:
            server().perform(
                    get( targetURL + "/3" )
            ).andExpect(
                    status().isNotFound()
            )
    }

}