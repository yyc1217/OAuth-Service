package tw.edu.ncu.cc.oauth.server.domain

import specification.SpringSpecification

class ApiTokenTest extends SpringSpecification {

    def "it can map to exist data"() {
        given:
            def token = ApiToken.include( [ 'client' ] ).get( 1 )
        expect:
            token.token == 'TOKEN1'
            token.client.name == 'APP1'
    }

}
