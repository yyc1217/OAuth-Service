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
<<<<<<< HEAD
                    ).andExpect( status().isOk() ).andReturn()
=======
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
>>>>>>> 84be543... add management controllers
            )
        then:
            response[0].user == "ADMIN1"
    }

    def "it can handle get of specified user's tokens 2"() {
        expect:
            server().perform(
                    get( targetURL + "/NOT_EXIST/token" )
<<<<<<< HEAD
            ).andExpect( status().isNotFound() )
=======
            ).andExpect(
                    status().isNotFound()
            )
>>>>>>> 84be543... add management controllers
    }

    def "it can handle get of specified user's clients 1"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/ADMIN1/application" )
<<<<<<< HEAD
                    ).andExpect( status().isOk() ).andReturn()
=======
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
>>>>>>> 84be543... add management controllers
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
<<<<<<< HEAD
                    ).andExpect( status().isOk() ).andReturn()
=======
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
>>>>>>> 84be543... add management controllers
            )
        then:
            response.name == "jason"
    }

<<<<<<< HEAD
}
=======
}
>>>>>>> 84be543... add management controllers
