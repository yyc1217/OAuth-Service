package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;

public interface ClientRepository {
    public void persistClient( ClientEntity client ) ;
    public void updateClient( ClientEntity client );
    public void deleteClient( ClientEntity client );
    public ClientEntity getClient( int clientID ) ;
}
