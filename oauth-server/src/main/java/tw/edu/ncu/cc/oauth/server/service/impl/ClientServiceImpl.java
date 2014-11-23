package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository;
import tw.edu.ncu.cc.oauth.server.repository.ClientTokenRepository;
import tw.edu.ncu.cc.oauth.server.service.ClientService;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private ClientTokenRepository clientTokenRepository;

    @Autowired
    public void setClientRepository( ClientRepository clientRepository ) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setClientTokenRepository( ClientTokenRepository clientTokenRepository ) {
        this.clientTokenRepository = clientTokenRepository;
    }

    @Override
    public void persistClient( ClientEntity client ) {
        clientRepository.persistClient( client );
    }

    @Override
    public void deleteClient( int clientID ) {
        clientRepository.deleteClient( loadClient( clientID ) );
    }

    @Override
    public void revokeClientTokens( int clientID ) {
        clientTokenRepository.deleteAllAccessTokenOfClient( loadClient( clientID ) );
    }

    @Override
    public ClientEntity regenerateClientSecret( int clientID ) {
        return null;//TODO IMPLEMENT
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public ClientEntity getClient( int clientID ) {
        return clientRepository.getClient( clientID );
    }

    private ClientEntity loadClient( int clientID ) {
        ClientEntity clientEntity = clientRepository.getClient( clientID );
        if( clientEntity != null ) {
            return clientEntity;
        } else {
            throw new RuntimeException( "client of id : [" + clientID + "] not exists" );
        }
    }

}
