package tw.edu.ncu.cc.oauth.db.data.model

import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.db.HibernateUtil
import tw.edu.ncu.cc.oauth.db.data.User
import tw.edu.ncu.cc.oauth.factory.HibernateUtilFactory


class UserModelImplTest extends Specification {

    private @Shared HibernateUtil hibernateUtil
    private UserModelImpl userModel

    def setupSpec() {
        HibernateUtilFactory hibernateUtilFactory = new HibernateUtilFactory()
        hibernateUtil = hibernateUtilFactory.provide();
    }

    def setup() {
        userModel = new UserModelImpl()
        userModel.setSession( hibernateUtil.currentSession() )
    }

    def cleanup() {
        hibernateUtil.closeSession()
    }

    def "use User(s) as the param of persistUsers() to persist the data into db"() {
        given:
            User user = new User()
            user.setName( "101502549" )
        when:
            userModel.persistUsers( user )
        then:
            userModel.getUser( "101502549" ) == user
    }

    def "getAuthCode() will return null if data not exist"() {
        expect:
            userModel.getUser( "USER NOT EXIST" ) == null
    }

}
