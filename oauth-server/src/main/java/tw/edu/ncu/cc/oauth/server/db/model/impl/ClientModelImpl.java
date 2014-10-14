package tw.edu.ncu.cc.oauth.server.db.model.impl;

import tw.edu.ncu.cc.oauth.server.db.data.ClientEntity;
import tw.edu.ncu.cc.oauth.server.db.model.ClientModel;
import tw.edu.ncu.cc.oauth.server.db.model.base.HibernateAccessTool;

public class ClientModelImpl extends HibernateAccessTool implements ClientModel {

    @Override
    public ClientEntity getClient( int clientID ) {
        return (ClientEntity ) getObject( clientID, ClientEntity.class );
    }

    @Override
    public void persistClient( ClientEntity... clients ) {
        persistObjects( ( Object[] ) clients );
    }

}
