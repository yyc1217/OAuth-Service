package tw.edu.ncu.cc.oauth.server.helper

import spock.lang.Specification


class StringHelperTest extends Specification {

    def "it can fetch first x characters from string"() {
        expect:
            StringHelper.first( str, length ) == result
        where:
            str        |  length | result
            'superman' |  3      | 'sup'
            'hello'    |  10     | 'hello'
    }

}
