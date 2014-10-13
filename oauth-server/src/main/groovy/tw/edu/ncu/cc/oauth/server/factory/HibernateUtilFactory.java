package tw.edu.ncu.cc.oauth.server.factory;

import org.glassfish.hk2.api.Factory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import tw.edu.ncu.cc.oauth.server.db.HibernateUtil;

public class HibernateUtilFactory implements Factory<HibernateUtil> {

    private static SessionFactory sessionFactory;

    @Override
    public HibernateUtil provide() {
        if( sessionFactory == null ) {
            sessionFactory = initSessionFactory();
        }
        return new HibernateUtil( sessionFactory );
    }

    private SessionFactory initSessionFactory() {
        try {

            Configuration conf = new Configuration() .configure();

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings( conf.getProperties() ).build();

            return conf.buildSessionFactory( serviceRegistry );

        } catch ( Throwable ex ) {
            throw new ExceptionInInitializerError( ex );
        }
    }

    @Override
    public void dispose( HibernateUtil instance ) {

    }

}
