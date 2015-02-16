package tw.edu.ncu.cc.oauth.server.helper

import spock.lang.Specification


class OAuthURLBuilderTest extends Specification {

    def "it can build redirect url"() {
        when:
            def url = OAuthURLBuilder
                    .url( "www.example.com" )
                    .error( "access_denied" )
                    .state( "abc" )
                    .build()
        then:
            url == "www.example.com?state=abc&error=access_denied"
    }

    def "it can build redirect url if param is not null"() {
        when:
            def url = OAuthURLBuilder
                    .url( "www.example.com" )
                    .error( "access_denied" )
                    .state( null )
                    .build()
        then:
            url == "www.example.com?error=access_denied"
    }

    def "it can build redirect url 3"() {
        when:
            def url = OAuthURLBuilder
                    .url( "www.example.com" )
                    .code( "code" )
                    .state( "abc" )
                    .build()
        then:
            url == "www.example.com?state=abc&code=code"
    }

    def "it would hide code if error exists"() {
        when:
            def url = OAuthURLBuilder
                    .url( "www.example.com" )
                    .error( "access_denied" )
                    .code( "code" )
                    .build()
        then:
            url == "www.example.com?error=access_denied"
    }

}
