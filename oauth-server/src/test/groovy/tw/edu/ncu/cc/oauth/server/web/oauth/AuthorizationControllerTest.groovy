package tw.edu.ncu.cc.oauth.server.web.oauth

import specification.IntegrationSpecification

import static helper.CustomMockMvcResponseMatchers.url
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class AuthorizationControllerTest extends IntegrationSpecification {

    def targetURL = "/oauth/authorize"

    def "it should return ok if params are correct"() {
        expect:
            server().perform(
                    get( targetURL ).with( user( "testman" ) )
                            .param( "state", "abc123" )
                            .param( "scope", "READ" )
                            .param( "client_id", "3" )
                            .param( "response_type", "code" )
            ).andExpect(
                    status().isOk()
            )
    }

    def "it should return 302 if user not logged in"() {
        expect:
            server().perform(
                    get( targetURL )
                            .param( "state", "abc123" )
                            .param( "scope", "READ" )
                            .param( "client_id", "3" )
                            .param( "response_type", "code" )
            ).andExpect(
                    status().isFound()
            )
    }

    def "it should work even state is not provided"() {
        expect:
            server().perform(
                    get( targetURL ).with( user( "testman" ) )
                            .param( "scope", "READ" )
                            .param( "client_id", "3" )
                            .param( "response_type", "code" )
            ).andExpect(
                    status().isOk()
            )
    }

    def "it should return badrequest if params are not correct"() {
        expect:
            server().perform(
                    get( targetURL ).with( user( "testman" ) )
            ).andExpect(
                    status().isBadRequest()
            )
    }

    def "it should return 302 if client is valid but scope is invalid"() {
        expect:
            server().perform(
                    get( targetURL ).with( user( "testman" ) )
                            .param( "scope", "NOTEXIST" )
                            .param( "client_id", "3" )
                            .param( "response_type", "code" )
            ).andExpect(
                    status().isFound()
            ).andExpect(
                    url().param( "error", "invalid_scope" )
            )
    }

    def "it should return 302 with state if client is valid and state is provided but scope is invalid"() {
        expect:
            server().perform(
                    get( targetURL ).with( user( "testman" ) )
                            .param( "state", "abc123" )
                            .param( "scope", "NOTEXIST" )
                            .param( "client_id", "3" )
                            .param( "response_type", "code" )
            ).andExpect(
                    status().isFound()
            ).andExpect(
                    url()
                        .param( "error", "invalid_scope" )
                        .param( "state", "abc123" )
            )
    }

    def "it should return 400 if client is invalid"() {
        expect:
            server().perform(
                    get( targetURL ).with( user( "testman" ) )
                            .param( "scope", "READ" )
                            .param( "client_id", "999" )
                            .param( "response_type", "code" )
            ).andExpect(
                    status().isBadRequest()
            )
    }

    def "it should return 400 if response_type is invalid"() { //TODO BUG ACTUAL IS 302
        expect:
            server().perform(
                    get( targetURL ).with( user( "testman" ) )
                            .param( "scope", "READ" )
                            .param( "client_id", "3" )
                            .param( "response_type", "NOTEXIST" )
            ).andExpect(
                    status().isBadRequest()
            )
    }

}
