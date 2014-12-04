package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;

public interface ClientService {

    public ClientEntity getClient ( int clientID ) ;
    public ClientEntity generateClient( ClientEntity client );
    public ClientEntity updateClient  ( ClientEntity client );
    public ClientEntity deleteClient  ( ClientEntity client );
    public ClientEntity refreshClientSecret( ClientEntity client );
    public void revokeClientTokens( ClientEntity client );
    public boolean isClientValid( int clientID, String clientSecret );

}
