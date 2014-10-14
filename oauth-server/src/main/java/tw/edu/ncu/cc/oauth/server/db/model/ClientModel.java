package tw.edu.ncu.cc.oauth.server.db.model;

import tw.edu.ncu.cc.oauth.server.db.data.ClientEntity;

public interface ClientModel {
    public ClientEntity getClient( int clientID ) ;
    public void persistClient( ClientEntity... clients ) ;
}
