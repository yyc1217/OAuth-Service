package tw.edu.ncu.cc.oauth.factory;

import org.glassfish.hk2.api.Factory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import tw.edu.ncu.cc.oauth.db.HibernateUtil;

public class HibernateUtilFactory implements Factory<HibernateUtil> {

    private final static SessionFactory sessionFactory;

    static {
        try {

            Configuration conf = new Configuration() .configure();

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();

            sessionFactory =  conf.buildSessionFactory(serviceRegistry);

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public HibernateUtil provide() {
        return new HibernateUtil( sessionFactory );
    }

    @Override
    public void dispose( HibernateUtil instance ) {

    }

}
