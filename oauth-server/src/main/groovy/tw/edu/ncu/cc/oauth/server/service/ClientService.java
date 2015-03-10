package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.data.v1.management.client.Client;
import tw.edu.ncu.cc.oauth.server.domain.ClientEntity;

public interface ClientService {

    public void revokeClientTokensByID( String id );
    public boolean isClientValid( String clientID, String clientSecret );

    public ClientEntity readClientByID( String id );
    public ClientEntity deleteClientByID( String id );
    public ClientEntity updateClient( String id, Client client );
    public ClientEntity createClient( Client client );
    public ClientEntity refreshClientSecret( String id );

}
