package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.transaction.annotation.Transactional
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class APITokenControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/api_tokens"

    @Transactional
    def "user can create api token by providing client id"() {
        given:
            def client = a_client()
        when:
            def response = JSON(
                    server().perform(
                            post( targetURL + "?client_id=" + serialId( client.id ) )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            server().perform(
                    get( targetURL + "/token/${response.token}"  )
            ).andExpect(
                    status().isOk()
            )
    }

    @Transactional
    def "user can get api token info by token"() {
        given:
            def apiToken = a_apiToken()
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/token/" + apiToken.token )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.client_id != null
    }

    @Transactional
    def "user can revoke api token info by id"() {
        given:
            def apiToken = a_apiToken()
        when:
            server().perform(
                    delete( targetURL + "/" + apiToken.id )
            ).andExpect(
                    status().isOk()
            )
        then:
            server().perform(
                    get( targetURL + "/token/" + apiToken.token )
            ).andExpect(
                    status().isNotFound()
            )
    }

    @Transactional
    def "user can refresh api token info by id"() {
        given:
            def apiToken = a_apiToken()
        expect:
            server().perform(
                    get( targetURL + "/token/" + apiToken.token )
            ).andExpect(
                    status().isOk()
            )
        when:
            def response = JSON(
                    server().perform(
                            post( targetURL + "/" + apiToken.id + "/refresh" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            server().perform(
                    get( targetURL + "/token/" + apiToken.token )
            ).andExpect(
                    status().isNotFound()
            )
        and:
            response.token != null
    }

}
