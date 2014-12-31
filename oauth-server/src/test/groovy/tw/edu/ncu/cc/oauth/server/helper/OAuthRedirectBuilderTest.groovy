package tw.edu.ncu.cc.oauth.server.helper

import spock.lang.Specification


class OAuthRedirectBuilderTest extends Specification {

    def "it can build redirect url 1"() {
        when:
            def url = OAuthRedirectBuilder
                    .callback( "www.example.com" )
                    .error( "access_denied" )
                    .state( null )
                    .build()
        then:
            url == "www.example.com?error=access_denied"
    }

    def "it can build redirect url 2"() {
        when:
            def url = OAuthRedirectBuilder
                    .callback( "www.example.com" )
                    .error( "access_denied" )
                    .state( "abc" )
                    .build()
        then:
            url == "www.example.com?state=abc&error=access_denied"
    }

    def "it can build redirect url 3"() {
        when:
            def url = OAuthRedirectBuilder
                    .callback( "www.example.com" )
                    .code( "code" )
                    .state( "abc" )
                    .build()
        then:
            url == "www.example.com?state=abc&code=code"
    }

}
