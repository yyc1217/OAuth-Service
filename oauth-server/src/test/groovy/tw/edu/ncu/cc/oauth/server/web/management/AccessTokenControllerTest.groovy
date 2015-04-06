package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.test.annotation.Rollback
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AccessTokenControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/access_tokens"

    def "user can get access token info by id"() {
        given:
            def accessToken = get_accessToken( 1 )
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
                            get( targetURL ).header( "token", accessToken.token )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.user == accessToken.user.name
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