package tw.edu.ncu.cc.oauth.server.db.model

import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.server.db.data.UserEntity
import tw.edu.ncu.cc.oauth.server.db.model.impl.UserModelImpl
import tw.edu.ncu.cc.oauth.server.resource.SessionResource
import tw.edu.ncu.cc.oauth.server.resource.UnitSessionResource


class UserModelImplTest extends Specification {

    @Shared  @ClassRule
    SessionResource sessionResource = new UnitSessionResource()

    UserModelImpl userModel

    def setup() {
        userModel = new UserModelImpl()
        userModel.setSession( sessionResource.getSession() )
    }

    def "it can persist and get UserEntity"() {
        given:
            UserEntity user = new UserEntity( "101502549" )
        when:
            userModel.persistUsers( user )
        then:
            userModel.getUser( "101502549" ) == user
    }

    def "it can init tokens if it's null"() {
        given:
            UserEntity user = new UserEntity( "TEACHER" )
        when:
            userModel.persistUsers( user )
        then:
            userModel.getUser( "TEACHER" ).getTokens() != null
    }

    def "it will return null if data not exist"() {
        expect:
            userModel.getUser( "USER NOT EXIST" ) == null
    }

}
