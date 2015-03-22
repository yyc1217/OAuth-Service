package tw.edu.ncu.cc.oauth.server.web.oauth

import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TokenExchangeControllerAuthCodeTest extends IntegrationSpecification {

    def targetURL = "/oauth/token"

    def "it should restrict the request of invalid grant_type"() {
        expect:
            server().perform(
                    post( targetURL )
                            .param( "grant_type", "error" )
                            .param( "client_id", serialId( 3 ) )
                            .param( "client_secret", "SECRET" )
                            .param( "code", "CODE" )
            ).andExpect(
                    status().isBadRequest()
            )
    }

    def "it should restrict the request of invalid client"() {
        expect:
            server().perform(
                    post( targetURL )
                            .param( "grant_type", "authorization_code" )
                            .param( "client_id", serialId( 4 ) )
                            .param( "client_secret", "SECRET" )
                            .param( "code", "CODE" )
            ).andExpect(
                    status().isBadRequest()
            )
    }

    def "it should restrict the request of invalid auth code"() {
        expect:
            server().perform(
                    post( targetURL )
                            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                            .param( "grant_type", "authorization_code" )
                            .param( "client_id", serialId( 3 ) )
                            .param( "client_secret", "SECRET" )
                            .param( "code", "INVALID" )
            ).andExpect(
                    status().isBadRequest()
            )
    }

    @Rollback
    def "it can exchange access token and refresh token with auth code"() {
        when:
            def response = JSON(
                    server().perform(
                            post( targetURL )
                                    .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                                    .param( "grant_type", "authorization_code" )
                                    .param( "client_id", serialId( 3 ) )
                                    .param( "client_secret", "SECRET" )
                                    .param( "code", "Mzo6OkNPREU=" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.access_token != null
            response.refresh_token != null
    }

}
