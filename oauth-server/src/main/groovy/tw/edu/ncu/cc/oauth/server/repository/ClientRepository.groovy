package tw.edu.ncu.cc.oauth.server.repository;

import tw.edu.ncu.cc.oauth.server.domain.ClientEntity;

public interface ClientRepository {
    public ClientEntity updateClient( ClientEntity client );
    public ClientEntity createClient( ClientEntity client ) ;
    public ClientEntity readClientByID( int clientID ) ;
    public void deleteClient( ClientEntity client );
    public void revokeClientCodes( ClientEntity client );
    public void revokeClientTokens( ClientEntity client );
}
