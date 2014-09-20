package tw.edu.ncu.cc.oauth.db.data.model.base;

import org.hibernate.Session;

import javax.inject.Inject;

public class SessionContainer {

    private Session session;

    @Inject
    public void setSession( Session session ) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }


}
