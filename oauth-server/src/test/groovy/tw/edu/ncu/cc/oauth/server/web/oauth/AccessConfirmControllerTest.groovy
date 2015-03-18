package tw.edu.ncu.cc.oauth.server.web.oauth

import specification.IntegrationSpecification
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.Permission

import static helper.CustomMockMvcResponseMatchers.url
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AccessConfirmControllerTest extends IntegrationSpecification {

    def targetURL = "/oauth/confirm"

    def "it should return 302 if user deny"() {
        given:
            def state = "abc123"
            def scope = [ Permission.get( 1 ) ] as Set< Permission >
            def client = Client.get( 1 )
        expect:
            server().perform(
                    post( targetURL )
                            .sessionAttr( "state", state )
                            .sessionAttr( "scope", scope )
                            .sessionAttr( "client", client )
                            .with( user( "ADMIN1" ) )
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

    def "it should return 302 if user agree"() {
        given:
            def state = "abc123"
            def scope = [ Permission.get( 1 ) ]
            def client = Client.get( 1 )
        expect:
            server().perform(
                    post( targetURL )
                            .sessionAttr( "state", state )
                            .sessionAttr( "scope", scope )
                            .sessionAttr( "client", client )
                            .with( user( "ADMIN1" ) )
                            .with( csrf() )
                            .param( "approval", "true" )
            ).andExpect(
                    status().isFound()
            ).andExpect(
                    url()
                            .param( "state", state )
                            .paramExist( "code" )
            )
    }

    def "it should return 403 if csrf not correct"() {
        expect:
            server().perform(
                    post( targetURL )
                            .with( user( "ADMIN1" ) )
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