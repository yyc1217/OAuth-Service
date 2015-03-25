package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.http.MediaType
import specification.IntegrationSpecification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class ClientControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/application"

    def "it can handle get of Client"() {
        given:
            def client = JSON(
                    server().perform(
                            get( targetURL + "/" + serialId( 1 ) )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        expect:
            client.name == 'APP1'
            client.url  == 'http://example.com'
            client.callback == 'http://example.com'
            client.description == '1111'
            client.owner == 'ADMIN1'
    }

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
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        expect:
            createResponse.name == 'app'
            createResponse.callback == 'http://example.com'
            createResponse.owner == 'ADMIN1'
            createResponse.secret   != null
            createResponse.apiToken != null
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
            ).andExpect(
                    status().isOk()
            )
        and:
            def getResponse = JSON(
                    server().perform(
                            get( targetURL + "/${createResponse.id}" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
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
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        when:
            server().perform(
                    delete( targetURL + "/${createResponse.id}" )
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
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        when:
            def getResponse = JSON(
                    server().perform(
                            post( targetURL + "/${createResponse.id}/secret" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            getResponse.secret != createResponse.secret
    }

    def "it can handle secret refresh of Client 2"() {
        expect:
            server().perform(
                    post( targetURL + "/123/secret" )
            ).andExpect(
                    status().isNotFound()
            )
    }

}