package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.http.MediaType
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

<<<<<<< HEAD
=======

>>>>>>> 84be543... add management controllers
class ClientControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/application"

    def "it can handle create and update of Client"() {
        given:
            def createResponse = JSON(
                    server().perform(
                            post( targetURL )
                                    .contentType( MediaType.APPLICATION_JSON )
                                    .content(
                                    '''
                                    {
                                      "name" : "app",
                                      "callback" : "http://example.com",
                                      "owner" : "ADMIN1"
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
        when:
            server().perform(
                    put( targetURL + "/${createResponse.id}" )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content(
<<<<<<< HEAD
                                    '''
=======
                            '''
>>>>>>> 84be543... add management controllers
                                    {
                                      "name" : "NEWNAME",
                                      "callback" : "http://example.com",
                                      "owner" : "ADMIN1"
                                    }
                                    '''
<<<<<<< HEAD
                            )
            ).andExpect( status().isOk() )
=======
                    )
            ).andExpect(
                    status().isOk()
            )
>>>>>>> 84be543... add management controllers
        and:
            def getResponse = JSON(
                    server().perform(
                            get( targetURL + "/${createResponse.id}" )
<<<<<<< HEAD
                    ).andExpect( status().isOk() ).andReturn()
=======
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
>>>>>>> 84be543... add management controllers
            )
        then:
            getResponse.name == "NEWNAME"
    }

    def "it can handle delete of Client"() {
        given:
            def createResponse = JSON(
                    server().perform(
                            post( targetURL )
                                    .contentType( MediaType.APPLICATION_JSON )
                                    .content(
                                    '''
                                    {
                                      "name" : "app",
                                      "callback" : "http://example.com",
                                      "owner" : "ADMIN1"
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
        when:
            server().perform(
                    delete( targetURL + "/${createResponse.id}" )
<<<<<<< HEAD
            ).andExpect( status().isOk() )
        then:
            server().perform(
                    get( targetURL + "/${createResponse.id}" )
            ).andExpect( status().isNotFound() )
    }

    def "it can handle secret refresh of Client"() {
=======
            ).andExpect(
                    status().isOk()
            )
        then:
            server().perform(
                    get( targetURL + "/${createResponse.id}" )
            ).andExpect(
                    status().isNotFound()
            )
    }

    def "it can handle secret refresh of Client 1"() {
>>>>>>> 84be543... add management controllers
        given:
            def createResponse = JSON(
                    server().perform(
                            post( targetURL )
                                    .contentType( MediaType.APPLICATION_JSON )
                                    .content(
                                    '''
                                    {
                                      "name" : "app",
                                      "callback" : "http://example.com",
                                      "owner" : "ADMIN1"
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
        when:
            def getResponse = JSON(
                    server().perform(
                            post( targetURL + "/${createResponse.id}/secret" )
<<<<<<< HEAD
                    ).andExpect( status().isOk() ).andReturn()
=======
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
>>>>>>> 84be543... add management controllers
            )
        then:
            getResponse.secret != createResponse.secret
    }

<<<<<<< HEAD
=======
    def "it can handle secret refresh of Client 2"() {
        expect:
            server().perform(
                    post( targetURL + "/123/secret" )
            ).andExpect(
                    status().isNotFound()
            )
    }

>>>>>>> 84be543... add management controllers
}
