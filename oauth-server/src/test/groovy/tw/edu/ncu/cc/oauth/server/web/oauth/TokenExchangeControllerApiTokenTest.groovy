package tw.edu.ncu.cc.oauth.server.web.oauth

import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TokenExchangeControllerApiTokenTest extends IntegrationSpecification {

    def targetURL = "/oauth/token"

    def "it should restrict the request of invalid client ( param version )"() {
        expect:
            server().perform(
                    post( targetURL )
                            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                            .param( "grant_type", "client_credentials" )
                            .param( "client_id", serialId( 4 ) )
                            .param( "client_secret", "SECRET" )
            ).andExpect(
                    status().isBadRequest()
            )
    }

    def "it should restrict the request of invalid client ( header version )"() {
        expect:
            server().perform(
                    post( targetURL )
                            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                            .param( "grant_type", "client_credentials" )
                            .header( "Authorization", basicAuth( serialId( 4 ), "SECRET" ) )
            ).andExpect(
                    status().isBadRequest()
            )
    }

    @Transactional
    def "it can exchange api token with client credentials ( param version )"() {
        when:
            def response = JSON(
                    server().perform(
                            post( targetURL )
                                    .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                                    .param( "grant_type", "client_credentials" )
                                    .param( "client_id", serialId( 3 ) )
                                    .param( "client_secret", "SECRET" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.access_token != null
    }

    @Transactional
    def "it can exchange api token with client credentials ( header version )"() {
        when:
            def response = JSON(
                    server().perform(
                            post( targetURL )
                                    .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                                    .param( "grant_type", "client_credentials" )
                                    .header( "Authorization", basicAuth( serialId( 3 ), "SECRET" ) )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.access_token != null
    }

    def basicAuth( String id, String secret ) {
        "Basic " + ( id + ":" + secret ).bytes.encodeBase64()
    }

}
