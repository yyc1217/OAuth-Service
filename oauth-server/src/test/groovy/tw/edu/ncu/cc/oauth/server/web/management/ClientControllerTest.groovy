package tw.edu.ncu.cc.oauth.server.web.management

import org.springframework.http.MediaType
import specification.IntegrationSpecification
import tw.edu.ncu.cc.oauth.data.v1.management.client.ClientObject

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class ClientControllerTest extends IntegrationSpecification {

    def targetURL = "/management/v1/client"

    def "user can get client by serial id"() {
        given:
            def client = get_client( 1 )
        when:
            def clientResponse = JSON(
                    server().perform(
                            get( targetURL + "/" + serialId( client.id ) )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            clientResponse.name        == client.name
            clientResponse.url         == client.url
            clientResponse.callback    == client.callback
            clientResponse.description == client.description
            clientResponse.owner       == client.owner.name
    }

    def "user can create and update client"() {
        given:
            def clientObject = new ClientObject(
                    name:     "app",
                    callback: "http://example.com",
                    owner:    "ADMIN1"
            )
            def createdClientObject = created_a_client( clientObject )
        expect:
            createdClientObject.name     == clientObject.name
            createdClientObject.callback == clientObject.callback
            createdClientObject.owner    == clientObject.owner
            createdClientObject.secret   != null
        when:
            update_a_client( createdClientObject.id, new ClientObject(
                    name:     "NEWNAME",
                    callback: createdClientObject.callback,
                    owner:    createdClientObject.owner
            ) )
        and:
            def getResponse = JSON(
                    server().perform(
                            get( targetURL + "/${createdClientObject.id}" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            getResponse.name == "NEWNAME"
    }

    def "user can delete client by serial id"() {
        given:
            def createdClientObject = created_a_client( new ClientObject(
                    name:     "app",
                    callback: "http://example.com",
                    owner:    "ADMIN1"
            ) )
        when:
            server().perform(
                    delete( targetURL + "/${ createdClientObject.id }" )
            ).andExpect(
                    status().isOk()
            )
        then:
            server().perform(
                    get( targetURL + "/${ createdClientObject.id }" )
            ).andExpect(
                    status().isNotFound()
            )
    }

    def "user can refresh secret of client by serial id 1"() {
        given:
            def createdClientObject = created_a_client( new ClientObject(
                    name:     "app",
                    callback: "http://example.com",
                    owner:    "ADMIN1"
            ) )
        when:
            def updatedClientObject = JSON(
                    server().perform(
                            post( targetURL + "/${ createdClientObject.id }/secret" )
                    ).andExpect(
                            status().isOk()
                    ).andReturn()
            )
        then:
            updatedClientObject.secret != createdClientObject.secret
    }

    def "user can refresh secret of client by serial id 2"() {
        expect:
            server().perform(
                    post( targetURL + "/123/secret" )
            ).andExpect(
                    status().isNotFound()
            )
    }

    private def created_a_client( ClientObject clientObject ) {
        JSON(
                server().perform(
                        post( targetURL )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content(
                            """
                                {
                                  "name" :     "${ clientObject.name }",
                                  "callback" : "${ clientObject.callback }",
                                  "owner" :    "${ clientObject.owner }"
                                }
                            """
                        )
                ).andExpect(
                        status().isOk()
                ).andReturn()
        )
    }

    private def update_a_client( id, ClientObject clientObject ) {
        JSON(
                server().perform(
                        put( targetURL + "/${ id }" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content(
                            """
                                {
                                  "name" :     "${ clientObject.name }",
                                  "callback" : "${ clientObject.callback }",
                                  "owner" :    "${ clientObject.owner }"
                                }
                            """
                        )
                ).andExpect(
                        status().isOk()
                ).andReturn()
        )

    }

}