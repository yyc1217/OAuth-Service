package tw.edu.ncu.cc.oauth.server.component.impl

import spock.lang.Specification
import tw.edu.ncu.cc.oauth.server.data.SerialSecret

class SecretCodecImplTest extends Specification {

    private SecretCodecImpl secretCodec = new SecretCodecImpl()

    def "it can encode and decode the Secret"() {
        given:
            def secret1 = new SerialSecret( 10, "secret" )
        when:
            def secret2 = secretCodec.decode( secretCodec.encode( secret1 ) )
        then:
            secret1.getId() == secret2.getId()
            secret1.getSecret() == secret2.getSecret()
    }

}
