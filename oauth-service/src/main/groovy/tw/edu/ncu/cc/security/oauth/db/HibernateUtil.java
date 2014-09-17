package tw.edu.ncu.cc.security.oauth.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {

            Configuration conf = new Configuration() .configure();

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();

            sessionFactory = conf.buildSessionFactory(serviceRegistry);

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static final ThreadLocal<Session> localSession = new ThreadLocal<>();

    public static Session currentSession() {
        Session session = localSession.get();
        if( session == null ) {
            session = sessionFactory.openSession();
            localSession.set( session );
        }
        return session;
    }

    public static void closeSession() {
        Session session = localSession.get();
        if (session != null)
            session.close();
        localSession.set( null );
    }

}