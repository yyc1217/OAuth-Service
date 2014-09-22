package tw.edu.ncu.cc.oauth.db.data.model.base;

import org.hibernate.Transaction;

public abstract class HibernateAccessTool extends SessionContainer {

    protected void persistObjects( Object... objects ) {
        Transaction transaction = getSession().beginTransaction();
        for( Object object : objects ) {
            getSession().persist( object );
        }
        transaction.commit();
    }

    protected void deleteObjects( Object... objects ) {
        Transaction transaction = getSession().beginTransaction();
        for( Object object : objects ) {
            getSession().delete( object );
        }
        transaction.commit();
    }

    protected Object getObject( int id, Class objectType ) {
        return getSession().get( objectType, id );
    }

}
