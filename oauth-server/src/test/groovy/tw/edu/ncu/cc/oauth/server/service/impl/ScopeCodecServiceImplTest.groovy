package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.service.ScopeCodecService


class ScopeCodecServiceImplTest extends SpringSpecification {

    @Autowired
    private ScopeCodecService scopeCodecService

    def "it can encode and decode the scope"() {
        given:
            def scope1 = [ "READ", "WRITE" ] as Set< String >
        when:
            def scope2 = scopeCodecService.decode( scopeCodecService.encode( scope1 ) )
        then:
            scope1 == scope2
    }

    def "it can check if all scope strings exist"() {
        given:
            def scope = set as Set< String >
        expect:
            scopeCodecService.exist( scope ) == exist
        where:
            set                 | exist
            [ "READ", "WRITE" ] | true
            [ "NOEXS", "READ" ] | false
    }

}
