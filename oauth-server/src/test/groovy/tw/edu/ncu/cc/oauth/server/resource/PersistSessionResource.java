package tw.edu.ncu.cc.oauth.server.resource;

import org.hibernate.Session;
import org.junit.rules.ExternalResource;
import tw.edu.ncu.cc.oauth.server.db.HibernateUtil;
import tw.edu.ncu.cc.oauth.server.factory.HibernateUtilFactory;

public class PersistSessionResource extends ExternalResource implements SessionResource {

    private HibernateUtil hibernateUtil;

    @Override
    protected void before() throws Throwable {
        hibernateUtil = new HibernateUtilFactory().provide();
    }

    @Override
    protected void after() {
        hibernateUtil.closeSession();
    }

    @Override
    public Session getSession() {
        return hibernateUtil.currentSession();
    }

}
