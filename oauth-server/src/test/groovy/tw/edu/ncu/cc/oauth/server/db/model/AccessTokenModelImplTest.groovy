package tw.edu.ncu.cc.oauth.server.db.model

import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.server.db.data.AccessTokenEntity
import tw.edu.ncu.cc.oauth.server.db.model.impl.AccessTokenModelImpl
import tw.edu.ncu.cc.oauth.server.resource.SessionResource
import tw.edu.ncu.cc.oauth.server.resource.UnitSessionResource


class AccessTokenModelImplTest extends Specification {

    @Shared  @ClassRule
    SessionResource sessionResource = new UnitSessionResource()

    AccessTokenModelImpl accessTokenModel

    def setup() {
        accessTokenModel = new AccessTokenModelImpl()
        accessTokenModel.setSession( sessionResource.getSession() )
    }

    def "it can persist and get AccessToken"() {
        given:
            def accessToken = new AccessTokenEntity( "ABC1234",null, null, null )
        when:
            accessTokenModel.persistAccessToken( accessToken )
        then:
            accessTokenModel.getAccessToken( "ABC1234" ) == accessToken
    }

    def "it can init scope if it's null"() {
        given:
            def accessToken = new AccessTokenEntity( "OTHER",null, null, null )
        when:
            accessTokenModel.persistAccessToken( accessToken )
        then:
            accessTokenModel.getAccessToken( "OTHER" ).getScope() != null
    }

    def "it will return null if data not exists"() {
        expect:
            accessTokenModel.getAccessToken( "TOKEN NOT EXIST" ) == null
    }

}
