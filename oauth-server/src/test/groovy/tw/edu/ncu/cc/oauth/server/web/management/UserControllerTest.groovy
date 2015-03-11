package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.http.MediaType
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class UserControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/user"

    def "it can handle get of specified user's tokens 1"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/ADMIN1/token" )
                    ).andExpect( status().isOk() ).andReturn()
            )
        then:
            response[0].user == "ADMIN1"
    }

    def "it can handle get of specified user's tokens 2"() {
        expect:
            server().perform(
                    get( targetURL + "/NOT_EXIST/token" )
            ).andExpect( status().isNotFound() )
    }

    def "it can handle get of specified user's clients 1"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/ADMIN1/application" )
                    ).andExpect( status().isOk() ).andReturn()
            )
        then:
            response[0].owner == "ADMIN1"
    }

    def "it can handle post of create new user"() {
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
                    ).andExpect( status().isOk() ).andReturn()
            )
        then:
            response.name == "jason"
    }

}
