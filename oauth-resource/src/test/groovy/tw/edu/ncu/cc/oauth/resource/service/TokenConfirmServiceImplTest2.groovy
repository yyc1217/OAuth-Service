package tw.edu.ncu.cc.oauth.resource.service

import org.junit.ClassRule
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import resource.ServerResource
import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.resource.config.RemoteConfig

class TokenConfirmServiceImplTest2 extends Specification {

    @Shared @ClassRule
    ServerResource serverResource = new ServerResource( 8898 )

    TokenConfirmService tokenConfirmService

    def setupSpec() {
        serverResource.mockServer().when(
                HttpRequest.request()
                        .withMethod( "GET" )
                        .withPath( "/management/v1/api_tokens/token/token1" )
        ).respond(
                HttpResponse.response()
                        .withStatusCode( 200 )
                        .withHeaders( new Header( "Content-Type", "application/json" ) )
                        .withBody(
                        '''
                        {
                            "id" : "123",
                            "client_id" : "abc",
                            "last_updated" : "2014-12-15"
                        }
                        '''
                )
        )
        serverResource.mockServer().when(
                HttpRequest.request()
                        .withMethod( "GET" )
                        .withPath( "/management/v1/api_tokens/token/token2" )
        ).respond(
                HttpResponse.response()
                        .withStatusCode( 404 )
        )
    }

    def setup() {
        tokenConfirmService = new TokenConfirmServiceImpl(
                new RemoteConfig(
                        serverPath: "http://localhost:" + serverResource.port()
                )
        )
    }

    def "it can get api token from remote server 1"() {
        expect:
            tokenConfirmService.readApiToken( "token1" ).client_id == "abc"
    }

    def "it can get api token from remote server 2"() {
        expect:
            tokenConfirmService.readApiToken( "token2" ) == null
    }

}
