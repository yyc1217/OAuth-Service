package tw.edu.ncu.cc.oauth.db.data.model

import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.db.HibernateUtil
import tw.edu.ncu.cc.oauth.db.data.AuthCode
import tw.edu.ncu.cc.oauth.factory.HibernateUtilFactory


class AuthCodemModelImplTest extends Specification {

    private @Shared HibernateUtil hibernateUtil
    private AuthCodemModelImpl authCodemModel

    def setupSpec() {
        HibernateUtilFactory hibernateUtilFactory = new HibernateUtilFactory()
        hibernateUtil = hibernateUtilFactory.provide()
    }

    def setup() {
        authCodemModel = new AuthCodemModelImpl()
        authCodemModel.setSession( hibernateUtil.currentSession() )
    }

    def cleanup() {
        hibernateUtil.closeSession()
    }

    def "use AuthCode(s) as the param of persistAuthToken() to persist the data into db"() {
        given:
            AuthCode authCode = new AuthCode()
            authCode.setCode( "AUTHCODE" )
        when:
            authCodemModel.persistAuthCode( authCode )
        then:
            authCodemModel.getAuthCode( "AUTHCODE" ) == authCode
    }

    def "getAuthCode() will return null if data not exist"() {
        expect:
            authCodemModel.getAuthCode( "AUTHCODE NOT EXIST" ) == null
    }

}
