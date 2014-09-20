package tw.edu.ncu.cc.oauth.db.data.model

import spock.lang.Shared
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.db.HibernateUtil
import tw.edu.ncu.cc.oauth.db.data.Client
import tw.edu.ncu.cc.oauth.factory.HibernateUtilFactory


class ClientModelImplTest extends Specification {

    private @Shared HibernateUtil hibernateUtil
    private ClientModelImpl clientModel

    def setupSpec() {
        HibernateUtilFactory hibernateUtilFactory = new HibernateUtilFactory()
        hibernateUtil = hibernateUtilFactory.provide()
    }

    def setup() {
        clientModel = new ClientModelImpl()
        clientModel.setSession( hibernateUtil.currentSession() )
    }

    def cleanup() {
        hibernateUtil.closeSession()
    }

    def "use Client(s) as the param of persistClient() to persist the data into db"() {
        given:
            Client client = new Client();
        when:
            clientModel.persistClient( client )
        then:
            clientModel.getClient( 1 ) == client
    }

    def "getClient() will return null if data not exist"() {
        expect:
            clientModel.getClient( 500 ) == null
    }

}
