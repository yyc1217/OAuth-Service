package tw.edu.ncu.cc.oauth.client.android

import spock.lang.Ignore
import spock.lang.Specification


class AndroidOauthBuilderTest extends Specification {

    @Ignore
    def "it can build a Oauth Manager"() {
        when:
            def manager = AndroidOauthBuilder
                    .initContext( null )
                    .fragmentManager( null )
                    .callback( "http://www.example.com" )
                    .clientID( "clientID" )
                    .clientSecret( "clientSecret" )
                    .scope( "READ", "WRITE" )
                    .build()
        then:
            manager != null
    }

}
