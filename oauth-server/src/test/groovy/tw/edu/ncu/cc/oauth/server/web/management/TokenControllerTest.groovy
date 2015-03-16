package tw.edu.ncu.cc.oauth.server.web.management

import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

<<<<<<< HEAD

=======
>>>>>>> 84be543... add management controllers
class TokenControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/token"

    def "it can handle get of specified AccessToken"() {
        when:
            def response = JSON(
<<<<<<< HEAD
                server().perform(
                        get( targetURL + "/1" )
                ).andExpect( status().isOk() ).andReturn()
=======
                    server().perform(
                            get( targetURL + "/1" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
>>>>>>> 84be543... add management controllers
            )
        then:
            response.user == "ADMIN1"
    }

<<<<<<< HEAD
    //TODO TEST DELETE ACTION

=======
>>>>>>> 84be543... add management controllers
}
