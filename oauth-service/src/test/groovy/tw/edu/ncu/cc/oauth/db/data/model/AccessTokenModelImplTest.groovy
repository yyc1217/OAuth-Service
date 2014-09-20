package tw.edu.ncu.cc.oauth.db.data.model

import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.db.HibernateUtil
import tw.edu.ncu.cc.oauth.db.data.AccessToken
import tw.edu.ncu.cc.oauth.factory.HibernateUtilFactory


class AccessTokenModelImplTest extends Specification {

    private @Shared HibernateUtil hibernateUtil
    private AccessTokenModelImpl accessTokenModel

    def setupSpec() {
        HibernateUtilFactory hibernateUtilFactory = new HibernateUtilFactory()
        hibernateUtil = hibernateUtilFactory.provide()
    }

    def setup() {
        accessTokenModel = new AccessTokenModelImpl()
        accessTokenModel.setSession( hibernateUtil.currentSession() )
    }

    def cleanup() {
        hibernateUtil.closeSession()
    }

    def "use AccessToken(s) as the param of persistAccessToken() to persist the data into db"() {
        given:
            def accessToken = new AccessToken()
            accessToken.setToken( "ABC1234" )
        when:
            accessTokenModel.persistAccessToken( accessToken )
        then:
            accessTokenModel.getAccessToken( "ABC1234" ) == accessToken
    }

    def "getAccessToken() will return null if data not exists"() {
        expect:
            accessTokenModel.getAccessToken( "TOKEN NOT EXIST" ) == null
    }

}
