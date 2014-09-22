package tw.edu.ncu.cc.oauth.db.data.model

import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.db.HibernateUtil
import tw.edu.ncu.cc.oauth.db.data.AuthCode
import tw.edu.ncu.cc.oauth.db.data.User
import tw.edu.ncu.cc.oauth.factory.HibernateUtilFactory


class AuthCodeAndUserTest extends Specification {

    private @Shared HibernateUtil hibernateUtil
    private AuthCodemModelImpl authCodemModel
    private UserModelImpl userModel

    def setupSpec() {
        HibernateUtilFactory hibernateUtilFactory = new HibernateUtilFactory()
        hibernateUtil = hibernateUtilFactory.provide()
    }

    def setup() {
        authCodemModel = new AuthCodemModelImpl()
        authCodemModel.setSession( hibernateUtil.currentSession() )

        userModel = new UserModelImpl()
        userModel.setSession( hibernateUtil.currentSession() )
    }

    def cleanup() {
        hibernateUtil.closeSession()
    }

    def "deleteAuthCode() can delete auth code referenced to  user"() {
        given:
            User user = new User()
            user.setName( "123456" )
        and:
            userModel.persistUsers( user )
        and:
            AuthCode authCode = new AuthCode()
            authCode.setCode( "ABCDEFG" )
            authCode.setUser( user )
        and:
            authCodemModel.persistAuthCodes( authCode )
        when:
            authCodemModel.deleteAuthCodes( authCode )
        then:
            notThrown( Exception )
        and:
            authCodemModel.getAuthCode( "ABCDEFG" ) == null

    }

}
