package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;

public interface ClientService {
    public void persistClient( ClientEntity client ) ;
    public void deleteClient ( int clientID );
    public void revokeClientTokens( int clientID );
    public ClientEntity getClient ( int clientID ) ;
}
