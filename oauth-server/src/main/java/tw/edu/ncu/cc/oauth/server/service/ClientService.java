package tw.edu.ncu.cc.oauth.server.service;

import tw.edu.ncu.cc.oauth.data.v1.management.application.Application;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;

public interface ClientService {

    public void revokeClientTokensByID( String id );
    public boolean isClientValid( String clientID, String clientSecret );

    public ClientEntity readClientByID( String id );
    public ClientEntity deleteClientByID( String id );
    public ClientEntity updateClient( String id, Application application );
    public ClientEntity createClient( Application application );
    public ClientEntity refreshClientSecret( String id );

}
