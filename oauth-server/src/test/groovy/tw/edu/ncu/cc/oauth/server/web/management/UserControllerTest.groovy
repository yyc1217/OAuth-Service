package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.http.MediaType
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class UserControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/user"

    def "user can get user's tokens 1"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/ADMIN1/tokens" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response[0].user == "ADMIN1"
    }

    def "user can get specified user's tokens 2"() {
        expect:
            server().perform(
                    get( targetURL + "/NOT_EXIST/tokens" )
            ).andExpect(
                    status().isNotFound()
            )
    }

    def "user can get specified user's clients 1"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/ADMIN1/applications" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response[0].owner == "ADMIN1"
    }

    def "user can create new user"() {
        when:
            def response = JSON(
                    server().perform(
                            post( targetURL )
                                    .contentType( MediaType.APPLICATION_JSON )
                                    .content(
                                    '''
                                    {
                                      "name" : "jason"
                                    }
                                    '''
                            )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.name == "jason"
    }

}