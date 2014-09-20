package tw.edu.ncu.cc.oauth.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateUtil {

    private SessionFactory sessionFactory;
    private final ThreadLocal<Session> localSession = new ThreadLocal<>();

    public HibernateUtil( SessionFactory sessionFactory ) {
        this.sessionFactory = sessionFactory;
    }

    public Session currentSession() {
        Session session = localSession.get();
        if( session == null ) {
            session = sessionFactory.openSession();
            localSession.set( session );
        }
        return session;
    }

    public void closeSession() {
        Session session = localSession.get();
        if (session != null)
            session.close();
        localSession.set( null );
    }


}