package tw.edu.ncu.cc.oauth.factory;

import org.glassfish.hk2.api.Factory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import tw.edu.ncu.cc.oauth.db.HibernateUtil;

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

            if( System.getProperty( "hibernate.connection.driver_class" ) != null ) {
                conf.setProperty( "hibernate.connection.driver_class",
                        System.getProperty( "hibernate.connection.driver_class" ) );
                conf.setProperty( "hibernate.connection.url",
                        System.getProperty( "hibernate.connection.url" ) );
                conf.setProperty( "hibernate.hbm2ddl.auto",
                        System.getProperty( "hibernate.hbm2ddl.auto" ) );
                conf.setProperty( "hibernate.connection.username",
                        System.getProperty( "hibernate.connection.username" ) );
                conf.setProperty( "hibernate.connection.password",
                        System.getProperty( "hibernate.connection.password" ) );
            }

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
