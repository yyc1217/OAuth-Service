package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.data.v1.management.application.Application;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.service.ClientFactory;
import tw.edu.ncu.cc.oauth.server.service.ClientService;
import tw.edu.ncu.cc.oauth.server.service.UserService;

@Service
public class ClientFactoryImpl implements ClientFactory {

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
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setUrl( application.getUrl() );
        clientEntity.setName( application.getName() );
        clientEntity.setCallback( application.getCallback() );
        clientEntity.setDescription( application.getDescription() );
        clientEntity.setOwner( userService.getUser( application.getOwner() ) );
        return clientService.generateClient( clientEntity );
    }

}
