package tool;

import org.glassfish.hk2.api.Factory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import tw.edu.ncu.cc.oauth.server.db.HibernateUtil;

public class HibernateUtilTestFactory implements Factory<HibernateUtil> {

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

            Configuration conf = new Configuration()
                    .configure()
                    .setProperty( "hibernate.connection.driver_class", "org.h2.Driver" )
                    .setProperty( "hibernate.connection.url", "jdbc:h2:mem:test" )
                    .setProperty( "hibernate.hbm2ddl.auto", "create" );

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
