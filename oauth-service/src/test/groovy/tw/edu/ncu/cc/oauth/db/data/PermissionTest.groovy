package tw.edu.ncu.cc.oauth.db.data

import spock.lang.Specification


class PermissionTest extends Specification {

    private Permission permission

    def setup() {
        permission = new Permission()
    }

    def "has property of id in Integer"() {
        when:
            permission.setId( 10 )
        then:
            permission.getId() == 10
    }

    def "has property of name in String"() {
        when:
            permission.setName( "REPO_READ" )
        then:
            permission.getName() == "REPO_READ"
    }

}
