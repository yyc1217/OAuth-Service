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
        ClientEntity targetClient = readClient( id );
        if( targetClient == null ) {
            return null;
        } else {
            targetClient.setUrl( application.getUrl() );
            targetClient.setName( application.getName() );
            targetClient.setCallback( application.getCallback() );
            targetClient.setDescription( application.getDescription() );
            targetClient.setOwner( userService.readUser( application.getOwner() ) );
            return clientService.updateClient( targetClient );
        }
    }

    @Override
    @Transactional
    public ClientEntity deleteClient( String id ) {
        ClientEntity targetClient = readClient( id );
        return targetClient == null ? null : clientService.deleteClient( targetClient );
    }

    @Override
    @Transactional
    public ClientEntity refreshClientSecret( String id ) {
        ClientEntity targetClient = readClient( id );
        return  targetClient == null ? null : clientService.refreshClientSecret( targetClient );
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
