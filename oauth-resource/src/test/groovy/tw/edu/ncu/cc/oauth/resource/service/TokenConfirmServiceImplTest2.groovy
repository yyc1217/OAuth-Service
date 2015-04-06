package tw.edu.ncu.cc.oauth.resource.service

import org.junit.ClassRule
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import resource.ServerResource
import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.resource.config.RemoteConfig

import static org.mockserver.model.Header.header


class TokenConfirmServiceImplTest2 extends Specification {

    @Shared @ClassRule
    ServerResource serverResource = new ServerResource( 8898 )

    TokenConfirmService tokenConfirmService

    def setupSpec() {
        serverResource.mockServer().when(
                HttpRequest.request()
                        .withMethod( "GET" )
                        .withPath( "/management/v1/api_tokens" )
                        .withHeaders(
                            header( "token", "token1" )
                        )
        ).respond(
                HttpResponse.response()
                        .withStatusCode( 200 )
                        .withHeaders( new Header( "Content-Type", "application/json" ) )
                        .withBody(
                        '''
                        {
                            "use_times" : 10,
                            "last_updated" : "2014-12-15"
                        }
                        '''
                )
        )
        serverResource.mockServer().when(
                HttpRequest.request()
                        .withMethod( "GET" )
                        .withPath( "/management/v1/api_tokens" )
                        .withHeaders(
                            header( "token", "token2" )
                        )
        ).respond(
                HttpResponse.response()
                        .withStatusCode( 404 )
        )
        serverResource.mockServer().when(
                HttpRequest.request()
                        .withMethod( "GET" )
                        .withPath( "/management/v1/api_tokens" )
                        .withHeaders(
                            header( "token", "token3" )
                        )
        ).respond(
                HttpResponse.response()
                        .withStatusCode( 403 )
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
            tokenConfirmService.readApiToken( "token1" ).use_times == 10
    }

    def "it can get api token from remote server 2"() {
        expect:
            tokenConfirmService.readApiToken( "token2" ) == null
    }

    def "it should get null if api token reach use limit"() {
        expect:
            tokenConfirmService.readApiToken( "token3" ) == null
    }

}
