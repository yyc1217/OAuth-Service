package tw.edu.ncu.cc.oauth.server.db.data.base

import spock.lang.Specification
import tw.edu.ncu.cc.oauth.data.Permission


class TokenEntityTest extends Specification {

    TokenEntity tokenEntity

    def setup(){
        tokenEntity = new TokenEntity()
    }

    def "it can contains Permissions 1"() {
        when:
            tokenEntity.setPermissions( Permission.CLASS_READ )
        then:
            tokenEntity.hasPermissions( Permission.CLASS_READ )
    }

    def "it can contains Permissions 2"() {
        expect:
            ! tokenEntity.hasPermissions( Permission.CLASS_READ )
    }

    def "do not set Permission in String"() {
        given:
            tokenEntity.setPermission( "" )
        when:
            tokenEntity.hasPermissions( Permission.CLASS_READ )
        then:
            thrown( StringIndexOutOfBoundsException )
    }

}
