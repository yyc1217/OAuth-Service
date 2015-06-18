package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.transaction.annotation.Transactional
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthorizedTokenControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/authorized_tokens"

    def "user can get authorized token info by id"() {
        given:
            def refreshToken = a_refreshToken()
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/" + refreshToken.id )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.user == refreshToken.user.name
    }

    @Transactional
    def "user can delete authorized token by id"() {
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