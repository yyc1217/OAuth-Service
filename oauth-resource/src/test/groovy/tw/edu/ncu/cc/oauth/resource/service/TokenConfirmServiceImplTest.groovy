package tw.edu.ncu.cc.oauth.resource.service

import org.junit.ClassRule
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import resource.ServerResource
import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.resource.config.RemoteConfig


class TokenConfirmServiceImplTest extends Specification {

    @Shared @ClassRule
    ServerResource serverResource = new ServerResource( 8898 )

    TokenConfirmService tokenConfirmService

    def setupSpec() {
        serverResource.mockServer().when(
                HttpRequest.request()
                        .withMethod( "GET" )
                        .withPath( "/token/token1/scope" )
        ).respond(
                HttpResponse.response()
                        .withStatusCode( 200 )
                        .withHeaders( new Header( "Content-Type", "application/json" ) )
                        .withBody( '[ "READ", "WRITE" ]' )
        )
        serverResource.mockServer().when(
                HttpRequest.request()
                        .withMethod( "GET" )
                        .withPath( "/token/token2/scope" )
        ).respond(
                HttpResponse.response()
                        .withStatusCode( 404 )
        )
    }

    def setup() {
        tokenConfirmService = new TokenConfirmServiceImpl()
        tokenConfirmService.setConfig( new RemoteConfig(
                addrPrefix: "http://localhost:${serverResource.port()}/token/",
                addrSuffix: "/scope"
        ) )
    }

    def "it can check token scope from remote server 1"() {
        expect:
            tokenConfirmService.readScope( "token1" ) == [ "READ", "WRITE" ]
    }

    def "it can check token scope from remote server 2"() {
        expect:
            tokenConfirmService.readScope( "token2" ) == null
    }

}
