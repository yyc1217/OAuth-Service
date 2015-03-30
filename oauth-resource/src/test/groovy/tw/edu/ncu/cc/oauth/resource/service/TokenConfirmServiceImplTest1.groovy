package tw.edu.ncu.cc.oauth.resource.service

import org.junit.ClassRule
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import resource.ServerResource
import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.resource.config.RemoteConfig


class TokenConfirmServiceImplTest1 extends Specification {

    @Shared @ClassRule
    ServerResource serverResource = new ServerResource( 8898 )

    TokenConfirmService tokenConfirmService

    def setupSpec() {
        serverResource.mockServer().when(
                HttpRequest.request()
                        .withMethod( "GET" )
                        .withPath( "/management/v1/access_token/token1" )
        ).respond(
                HttpResponse.response()
                        .withStatusCode( 200 )
                        .withHeaders( new Header( "Content-Type", "application/json" ) )
                        .withBody(
                        '''
                        {
                            "id" : "1",
                            "user" : "101502549",
                            "scope" : [ "READ", "WRITE" ],
                            "last_updated" : "2014-12-15"
                        }
                        '''
                )
        )
        serverResource.mockServer().when(
                HttpRequest.request()
                        .withMethod( "GET" )
                        .withPath( "/management/v1/access_token/token2" )
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

    def "it can get access token from remote server 1"() {
        expect:
            tokenConfirmService.readAccessToken( "token1" ).scope.toList() == [ "READ", "WRITE" ]
    }

    def "it can get access token from remote server 2"() {
        expect:
            tokenConfirmService.readAccessToken( "token2" ) == null
    }

}
