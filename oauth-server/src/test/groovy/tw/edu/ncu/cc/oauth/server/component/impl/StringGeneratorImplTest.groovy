package tw.edu.ncu.cc.oauth.server.component.impl

import spock.lang.Specification


class StringGeneratorImplTest extends Specification {

    private StringGeneratorImpl stringGenerator = new StringGeneratorImpl()

    def "it can generate random string"() {
        expect:
            stringGenerator.generateToken() != stringGenerator.generateToken()
    }

}
