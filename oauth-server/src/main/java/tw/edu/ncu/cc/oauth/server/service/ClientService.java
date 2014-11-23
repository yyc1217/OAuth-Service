package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;

public interface ClientService {

    public ClientEntity getClient ( int clientID ) ;
    public void generateClient( ClientEntity client );
    public void updateClient  ( ClientEntity client );
    public void deleteClient  ( ClientEntity client );
    public void revokeClientTokens( ClientEntity client );

}
