package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;

public interface ClientRepository {
    public void deleteClient( ClientEntity client );
    public ClientEntity updateClient( ClientEntity client );
    public ClientEntity generateClient( ClientEntity client ) ;
    public ClientEntity getClient( int clientID ) ;
}
