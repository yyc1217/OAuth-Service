package tw.edu.ncu.cc.oauth.server.helper

import spock.lang.Specification
import tw.edu.ncu.cc.oauth.server.domain.Permission


class ScopeHelperTest extends Specification {

    def "it can convert permission set to string array with name"() {
        given:
            Set< Permission > scope = [
                    new Permission( name: 'p1' ) ,
                    new Permission( name: 'p2' )
            ]
        when:
            def arr = ScopeHelper.toStringArray( scope )
        then:
            arr[0] == 'p1'
            arr[1] == 'p2'
    }
}
