package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ManagerControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/managers"

    @Transactional
    def "user can get all managers' information"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL )
                                    .param( "page", "0" )
                                    .param( "size", "3" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.size() == 1
    }

    @Transactional
    def "user can get manager's information"() {
        when:
            def response = JSON(
                    server().perform(
                            get( targetURL + "/ADMIN1" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.id == "ADMIN1"
    }

    @Transactional
    def "user can create manager 1"() {
        when:
            server().perform(
                    post( targetURL )
                    .contentType( MediaType.APPLICATION_JSON )
                    .content(
                        """
                        {
                          "id" : "ADMIN2"
                        }
                        """
                    )
            ).andExpect(
                    status().isOk()
            )
        then:
            server().perform(
                    get( targetURL + "/ADMIN2" )
            ).andExpect(
                    status().isOk()
            )
    }

    @Transactional
    def "user can create manager 2"() {
        when:
            server().perform(
                    post( targetURL )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content(
                            """
                        {
                          "id" : "ADMIN_NOT_EXIST"
                        }
                        """
                    )
            ).andExpect(
                    status().isOk()
            )
        then:
            server().perform(
                    get( targetURL + "/ADMIN_NOT_EXIST" )
            ).andExpect(
                    status().isOk()
            )
    }

    @Transactional
    def "user can delete manager 1"() {
        when:
            def response = JSON(
                    server().perform(
                            delete( targetURL + "/ADMIN1" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            response.id == "ADMIN1"
    }

    @Transactional
    def "user can delete manager 2"() {
        expect:
            server().perform(
                    delete( targetURL + "/ADMIN_NOT_EXIST" )
            ).andExpect(
                    status().isNotFound()
            )
    }

}
