package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.data.v1.management.application.Application;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.service.ClientAPIService;
import tw.edu.ncu.cc.oauth.server.service.ClientService;
import tw.edu.ncu.cc.oauth.server.service.UserService;

@Service
public class ClientAPIServiceImpl implements ClientAPIService {

    private UserService userService;
    private ClientService clientService;

    @Autowired
    public void setUserService( UserService userService ) {
        this.userService = userService;
    }

    @Autowired
    public void setClientService( ClientService clientService ) {
        this.clientService = clientService;
    }

    @Override
    @Transactional
    public ClientEntity createClient( Application application ) {
        return clientService.createClient( buildClientEntity( application ) );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public ClientEntity readClient( String id ) {
        return clientService.readClient( Integer.parseInt( id ) );
    }

    @Override
    @Transactional
    public ClientEntity updateClient( String id, Application application ) {
        ClientEntity newClient = buildClientEntity( application );
        ClientEntity oldClient = readClient( id );
        oldClient.setUrl( newClient.getUrl() );
        oldClient.setName( newClient.getName() );
        oldClient.setCallback( newClient.getCallback() );
        oldClient.setDescription( newClient.getDescription() );
        oldClient.setOwner( newClient.getOwner() );
        return clientService.updateClient( oldClient );
    }

    @Override
    @Transactional
    public ClientEntity deleteClient( String id ) {
        return clientService.deleteClient( readClient( id ) );
    }

    @Override
    @Transactional
    public ClientEntity refreshClientSecret( String id ) {
        return clientService.refreshClientSecret( readClient( id ) );
    }

    private ClientEntity buildClientEntity( Application application ) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setUrl( application.getUrl() );
        clientEntity.setName( application.getName() );
        clientEntity.setCallback( application.getCallback() );
        clientEntity.setDescription( application.getDescription() );
        clientEntity.setOwner( userService.readUser( application.getOwner() ) );
        return clientEntity;
    }

}
