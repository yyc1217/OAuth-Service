package tw.edu.ncu.cc.oauth.server.db.model

import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.server.db.data.ClientEntity
import tw.edu.ncu.cc.oauth.server.db.model.impl.ClientModelImpl
import tw.edu.ncu.cc.oauth.server.resource.SessionResource
import tw.edu.ncu.cc.oauth.server.resource.UnitSessionResource


class ClientModelImplTest extends Specification {

    @Shared  @ClassRule
    SessionResource sessionResource = new UnitSessionResource()

    ClientModelImpl clientModel

    def setup() {
        clientModel = new ClientModelImpl()
        clientModel.setSession( sessionResource.getSession() )
    }

    def "it can persist and get ClientEntity"() {
        given:
            ClientEntity client = new ClientEntity();
        when:
            clientModel.persistClient( client )
        then:
            clientModel.getClient( 1 ) == client
    }

    def "it will return null if data not exist"() {
        expect:
            clientModel.getClient( 500 ) == null
    }

}
