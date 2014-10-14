package tw.edu.ncu.cc.oauth.server.db.model.base;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.inject.Inject;
import java.util.List;

public abstract class HibernateAccessTool {

    private Session session;

    @Inject
    public void setSession( Session session ) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

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

    protected <T> T getObject( Class<T> clazz, Query query ) {
        List<T> list = getObjects( clazz, query );
        if( list.size() != 1 ) {
            return null;
        }else {
            return list.get( 0 );
        }
    }

    @SuppressWarnings( "unchecked" )
    protected <T> List<T> getObjects( Class<T> clazz, Query query ) {
        return ( List<T> ) query.list();
    }

}
