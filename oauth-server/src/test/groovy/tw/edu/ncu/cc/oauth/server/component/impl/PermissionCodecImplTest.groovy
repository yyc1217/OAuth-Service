package tw.edu.ncu.cc.oauth.server.component.impl

import spock.lang.Specification
import tw.edu.ncu.cc.oauth.data.Permission


class PermissionCodecImplTest extends Specification {

    private PermissionCodecImpl permissionCodec = new PermissionCodecImpl();

    def "it can decode and encode the Permission"() {
        given:
            def scope = [ Permission.CLASS_READ.name() ] as Set< String >
        when:
            def scopeSet = permissionCodec.decode( permissionCodec.encode( scope ) )
        then:
            scopeSet.contains( "CLASS_READ" )
    }

}
