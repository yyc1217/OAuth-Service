package tw.edu.ncu.cc.oauth.server.db.model

import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.server.db.data.AuthCodeEntity
import tw.edu.ncu.cc.oauth.server.db.model.impl.AuthCodemModelImpl
import tw.edu.ncu.cc.oauth.server.resource.SessionResource
import tw.edu.ncu.cc.oauth.server.resource.UnitSessionResource


class AuthCodemModelImplTest extends Specification {

    @Shared  @ClassRule
    SessionResource sessionResource = new UnitSessionResource()

    AuthCodemModelImpl authCodemModel

    def setup() {
        authCodemModel = new AuthCodemModelImpl()
        authCodemModel.setSession( sessionResource.getSession() )
    }

    def "it can persist and get AuthCodeEntity"() {
        given:
            AuthCodeEntity authCode = new AuthCodeEntity( "ABC", null, null, null )
        when:
            authCodemModel.persistAuthCodes( authCode )
        then:
            authCodemModel.getAuthCode( "ABC" ) == authCode
    }

    def "it can delete AuthCodeEntity"() {
        given:
            AuthCodeEntity authCode = new AuthCodeEntity( "EFG", null, null, null )
        when:
            authCodemModel.persistAuthCodes( authCode )
            authCodemModel.deleteAuthCodes( authCode )
        then:
            authCodemModel.getAuthCode( "EFG" ) == null
    }

    def "it can init scope if it's null"() {
        given:
            AuthCodeEntity authCode = new AuthCodeEntity( "KLM", null, null, null )
        when:
            authCodemModel.persistAuthCodes( authCode )
        then:
            authCodemModel.getAuthCode( "KLM" ).getScope() != null
    }

    def "it will return null if data not exist"() {
        expect:
            authCodemModel.getAuthCode( "AUTHCODE NOT EXIST" ) == null
    }

}
