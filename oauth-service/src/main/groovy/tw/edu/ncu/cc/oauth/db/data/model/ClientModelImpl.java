package tw.edu.ncu.cc.oauth.db.data.model;

import tw.edu.ncu.cc.oauth.db.data.Client;
import tw.edu.ncu.cc.oauth.db.data.model.abstracts.ClientModel;
import tw.edu.ncu.cc.oauth.db.data.model.base.HibernateAccessTool;

public class ClientModelImpl extends HibernateAccessTool implements ClientModel {

    @Override
    public Client getClient( int clientID ) {
        return (Client) getObject( clientID, Client.class );
    }

    @Override
    public void persistClient( Client... clients ) {
        persistObject( ( Object[] ) clients );
    }

}
