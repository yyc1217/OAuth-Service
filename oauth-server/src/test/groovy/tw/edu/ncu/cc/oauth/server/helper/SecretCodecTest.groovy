package tw.edu.ncu.cc.oauth.server.helper

import spock.lang.Specification
import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret

class SecretCodecTest extends Specification {

    private SecretCodec secretCodec = new SecretCodec()

    def "it can encode and decode the Secret"() {
        given:
            def secret1 = new SerialSecret( 10, "secret" )
        when:
            def secret2 = secretCodec.decode( secretCodec.encode( secret1 ) )
        then:
            secret1.getId() == secret2.getId()
            secret1.getSecret() == secret2.getSecret()
    }

    def "it will decode invalid format of String into Secret with specified value"() {
        when:
            def secret = secretCodec.decode( code )
        then:
            secret.getId() == 0
            secret.getSecret() == ""
        where:
            code << [ "CODE:S", "MTIzSGVsbG8=", "MTIzOjpIZWxsbw==" ]
    }

}
