package tw.edu.ncu.cc.oauth.db.data.model.abstracts;

import tw.edu.ncu.cc.oauth.db.data.Client;

public interface ClientModel {
    public Client getClient( int clientID ) ;
    public void persistClient( Client... clients ) ;
}
