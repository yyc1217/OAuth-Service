package tw.edu.ncu.cc.oauth.server.factory;

import org.glassfish.hk2.api.Factory;
import org.hibernate.Session;
import tw.edu.ncu.cc.oauth.server.db.HibernateUtil;

import javax.inject.Inject;

public class HibernateSessionFactory implements Factory<Session> {

    private HibernateUtil hibernateUtil;

    @Inject
    public HibernateSessionFactory( HibernateUtil hibernateUtil ) {
        this.hibernateUtil = hibernateUtil;
    }

    @Override
    public Session provide() {
        return hibernateUtil.currentSession();
    }

    @Override
    public void dispose( Session instance ) {
        hibernateUtil.closeSession();
    }

}
