package tw.edu.ncu.cc.oauth.db.data.model

import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.db.HibernateUtil
import tw.edu.ncu.cc.oauth.db.data.Permission
import tw.edu.ncu.cc.oauth.factory.HibernateUtilFactory


class PermissionModelImplTest extends Specification {

    private @Shared HibernateUtil hibernateUtil
    private PermissionModelImpl permissionModel

    def setupSpec() {
        HibernateUtilFactory hibernateUtilFactory = new HibernateUtilFactory()
        hibernateUtil = hibernateUtilFactory.provide();
    }

    def setup() {
        permissionModel = new PermissionModelImpl()
        permissionModel.setSession( hibernateUtil.currentSession() )
    }

    def cleanup() {
        hibernateUtil.closeSession()
    }

    def "use Permission(s) as the param of persistClient() to persist the data into db"() {
        given:
            Permission permission = new Permission()
            permission.setName( "P1" )
        when:
            permissionModel.persistPermissions( permission )
        then:
            permissionModel.getAllPermissions().contains( permission )
    }

    def "convertToPermissions() can only handle the data in db before startup"() { //TODO COMMON TEST
        given:
            Permission permission = new Permission()
            permission.setName( "CONVERT" )
        and:
            permissionModel.persistPermissions( permission )
        and:
            def permissionSet = new HashSet<String>()
            permissionSet.add( "CONVERT" )
        when:
            permissionModel.convertToPermissions( permissionSet )
        then:
            thrown( IllegalArgumentException )
    }

    def "convertToPermissions() will thorw exception if permission not exist"() {
        given:
            def permissionSet = new HashSet<String>()
            permissionSet.add( "NOT" )
        when:
            permissionModel.convertToPermissions( permissionSet )
        then:
            thrown( IllegalArgumentException )
    }

    def "use isPermissionsExist() to check if all permissions exist"() {
        given:
            def permissionSet = new HashSet<String>()
            permissionSet.add( "PERMISSION NOT EXIST" )
        expect:
            ! permissionModel.isPermissionsExist( permissionSet )
    }

}
