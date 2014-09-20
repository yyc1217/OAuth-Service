package tw.edu.ncu.cc.oauth.db.data.model

import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.db.HibernateUtil
import tw.edu.ncu.cc.oauth.db.data.AccessToken
import tw.edu.ncu.cc.oauth.db.data.Permission
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.AccessTokenModel
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.PermissionModel
import tw.edu.ncu.cc.oauth.factory.HibernateUtilFactory


class DataReferenceTest extends Specification {

    private @Shared HibernateUtil hibernateUtil
    private @Shared PermissionModel permissionModel
    private @Shared AccessTokenModel accessTokenModel

    def setupSpec() {
        HibernateUtilFactory hibernateUtilFactory = new HibernateUtilFactory()
        hibernateUtil = hibernateUtilFactory.provide()

        accessTokenModel = new AccessTokenModelImpl()
        accessTokenModel.setSession( hibernateUtil.currentSession() )

        permissionModel = new PermissionModelImpl()
        permissionModel.setSession( hibernateUtil.currentSession() )

        savePreData()
    }

    private void savePreData() {
        Permission permission1 = new Permission()
        Permission permission2 = new Permission()
        permission1.setName( "CLASS_READ" )
        permission2.setName( "CLASS_WRITE" )
        permissionModel.persistPermissions( permission1, permission2 )
    }

    def cleanupSpec() {
        hibernateUtil.closeSession()
    }

    def "persist a data which reference to exist data"() {
        given:
            Set<String> permissionStrings = new HashSet<>()
            permissionStrings.add( "CLASS_READ" )
            permissionStrings.add( "CLASS_WRITE" )
        and:
            Set<Permission> permissions = permissionModel.convertToPermissions( permissionStrings )
        and:
            AccessToken accessToken = new AccessToken()
            accessToken.setToken( "HELLO" )
            accessToken.setScope( permissions )
        when:
            accessTokenModel.persistAccessToken( accessToken )
        then:
            notThrown( Exception )
    }

}
