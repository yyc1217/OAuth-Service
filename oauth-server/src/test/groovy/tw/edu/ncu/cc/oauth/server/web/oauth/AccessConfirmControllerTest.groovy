package tw.edu.ncu.cc.oauth.server.web.oauth

import specification.IntegrationSpecification
import tw.edu.ncu.cc.oauth.server.domain.ClientEntity

import static helper.CustomMockMvcResponseMatchers.url
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class AccessConfirmControllerTest extends IntegrationSpecification {

    def targetURL = "/oauth/confirm"

    def "it should return ok if params are correct"() {

        given:
            def state = "abc123"
            def scope = [ "READ" ] as Set< String >
            def client = new ClientEntity( id: 3, callback: "example.com" )
        expect:
            server().perform(
                    post( targetURL )
                            .sessionAttr( "state", state )
                            .sessionAttr( "scope", scope )
                            .sessionAttr( "client", client )
                            .with( user( "testman" ) )
                            .with( csrf() )
                            .param( "approval", "false" )
            ).andExpect(
                    status().isFound()
            ).andExpect(
                    url()
                        .param( "error", "access_denied" )
                        .param( "state", state )
            )
    }

    def "it should return 403 if csrf not correct"() {
        expect:
            server().perform(
                    post( targetURL )
                            .with( user( "testman" ) )
            ).andExpect(
                    status().isForbidden()
            )
    }

    def "it should return 403 if user not logged in"() {
        expect:
            server().perform(
                    post( targetURL )
            ).andExpect(
                    status().isForbidden()
            )
    }


}
