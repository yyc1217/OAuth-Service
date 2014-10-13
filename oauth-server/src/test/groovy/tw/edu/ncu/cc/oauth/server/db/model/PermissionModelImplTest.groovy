package tw.edu.ncu.cc.oauth.server.db.model

import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.server.db.data.PermissionEntity
import tw.edu.ncu.cc.oauth.server.db.model.impl.PermissionModelImpl
import tw.edu.ncu.cc.oauth.server.resource.SessionResource
import tw.edu.ncu.cc.oauth.server.resource.UnitSessionResource


class PermissionModelImplTest extends Specification {

    @Shared  @ClassRule
    SessionResource sessionResource = new UnitSessionResource()

    PermissionModelImpl permissionModel

    def setup() {
        permissionModel = new PermissionModelImpl()
        permissionModel.setSession( sessionResource.getSession() )
    }

    def "it can persist and get PermissionEntity"() {
        given:
            PermissionEntity permission = new PermissionEntity( "P1" )
        when:
            permissionModel.persistPermissions( permission )
        then:
            permissionModel.getAllPermissions().contains( permission )
    }

    def "it can convert String to existing PermissionEntity"() { //TODO COMMON TEST
        given:
            permissionModel.persistPermissions( new PermissionEntity( "CONVERT" ) )
        and:
            def permissionSet = new HashSet<String>()
            permissionSet.add( "CONVERT" )
        when:
            permissionModel.convertToPermissions( permissionSet )
        then:
            notThrown( IllegalArgumentException )
    }

    def "it will thorw exception if permission not exist"() {
        given:
            def permissionSet = new HashSet<String>()
            permissionSet.add( "NOT" )
        when:
            permissionModel.convertToPermissions( permissionSet )
        then:
            thrown( IllegalArgumentException )
    }

    def "it can check if a set of String Permission exist"() {
        given:
            def permissionSet = new HashSet<String>()
            permissionSet.add( "PERMISSION NOT EXIST" )
        expect:
            ! permissionModel.isPermissionsExist( permissionSet )
    }

}
