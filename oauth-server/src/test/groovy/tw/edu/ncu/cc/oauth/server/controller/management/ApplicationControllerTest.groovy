package tw.edu.ncu.cc.oauth.server.controller.management

import org.springframework.http.MediaType
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ApplicationControllerTest extends IntegrationSpecification {

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
                    ).andExpect( status().isOk() ).andReturn()
            )
        when:
            server().perform(
                    put( targetURL + "/${createResponse.id}" )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content(
                                    '''
                                    {
                                      "name" : "NEWNAME",
                                      "callback" : "http://example.com",
                                      "owner" : "ADMIN1"
                                    }
                                    '''
                    )
            ).andExpect( status().isOk() )
        and:
            def getResponse = JSON(
                    server().perform(
                            get( targetURL + "/${createResponse.id}" )
                    ).andExpect( status().isOk() ).andReturn()
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
                    ).andExpect( status().isOk() ).andReturn()
            )
        when:
            server().perform(
                    delete( targetURL + "/${createResponse.id}" )
            ).andExpect( status().isOk() )
        then:
            server().perform(
                    get( targetURL + "/${createResponse.id}" )
            ).andExpect( status().isNotFound() )
    }

    def "it can handle secret refresh of Client"() {
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
                    ).andExpect( status().isOk() ).andReturn()
            )
        when:
            def getResponse = JSON(
                    server().perform(
                            post( targetURL + "/${createResponse.id}/secret" )
                    ).andExpect( status().isOk() ).andReturn()
            )
        then:
            getResponse.secret != createResponse.secret
    }

}
