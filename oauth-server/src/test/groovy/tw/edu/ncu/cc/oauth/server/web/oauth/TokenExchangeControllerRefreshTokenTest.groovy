package tw.edu.ncu.cc.oauth.server.web.oauth

import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TokenExchangeControllerRefreshTokenTest extends IntegrationSpecification {

    def targetURL = "/oauth/token"

    def "it should restrict the request of invalid client"() {
        expect:
            server().perform(
                    post( targetURL )
                            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                            .param( "grant_type", "refresh_token" )
                            .param( "client_id", serialId( 4 ) )
                            .param( "client_secret", "SECRET" )
                            .param( "refresh_token", "Mzo6OlRPS0VO" )
            ).andExpect(
                    status().isBadRequest()
            )
    }

    def "it should restrict the request of invalid refresh token"() {
        expect:
            server().perform(
                    post( targetURL )
                            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                            .param( "grant_type", "refresh_token" )
                            .param( "client_id", serialId( 3 ) )
                            .param( "client_secret", "SECRET" )
                            .param( "refresh_token", "INVALID" )
            ).andExpect(
                    status().isBadRequest()
            )
    }

    @Transactional
    def "it can exchange access token with refresh token"() {
        when:
            def response = JSON(
                    server().perform(
                            post( targetURL )
                                    .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                                    .param( "grant_type", "refresh_token" )
                                    .param( "client_id", serialId( 3 ) )
                                    .param( "client_secret", "SECRET" )
                                    .param( "refresh_token", "Mzo6OlRPS0VO" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.access_token != null
    }

}
