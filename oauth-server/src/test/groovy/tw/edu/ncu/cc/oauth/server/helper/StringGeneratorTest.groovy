package tw.edu.ncu.cc.oauth.server.helper

import spock.lang.Specification

class StringGeneratorTest extends Specification {

    private StringGenerator stringGenerator = new StringGenerator()

    def "it can generate random string"() {
        expect:
            stringGenerator.generateToken() != stringGenerator.generateToken()
    }

}
