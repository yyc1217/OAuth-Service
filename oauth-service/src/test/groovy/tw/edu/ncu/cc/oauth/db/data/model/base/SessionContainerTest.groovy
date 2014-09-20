package tw.edu.ncu.cc.oauth.db.data.model.base

import org.hibernate.Session
import spock.lang.Specification


class SessionContainerTest extends Specification {

    private SessionContainer sessionContainer;

    def setup() {
        sessionContainer = new SessionContainer();
    }

    def "has the property of session in Hibernate Session"() {
        given:
            def session = Mock( Session )
        when:
            sessionContainer.setSession( session )
        then:
            sessionContainer.getSession() == session
    }

}
