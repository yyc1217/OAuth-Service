package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository;
import tw.edu.ncu.cc.oauth.server.repository.ClientTokenRepository;
import tw.edu.ncu.cc.oauth.server.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

    private PasswordEncoder encoder;
    private ClientRepository clientRepository;
    private ClientTokenRepository clientTokenRepository;

    @Autowired
    public void setEncoder( PasswordEncoder encoder ) {
        this.encoder = encoder;
    }

    @Autowired
    public void setClientRepository( ClientRepository clientRepository ) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setClientTokenRepository( ClientTokenRepository clientTokenRepository ) {
        this.clientTokenRepository = clientTokenRepository;
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public ClientEntity getClient( int clientID ) {
        return clientRepository.getClient( clientID );
    }

    @Override
    public void updateClient( ClientEntity client ) {
        clientRepository.updateClient( client );
    }

    @Override
    @Transactional
    public void deleteClient( ClientEntity client ) {
        clientRepository.deleteClient( client );
    }

    @Override
    @Transactional
    public void revokeClientTokens( ClientEntity client ) {
        clientTokenRepository.deleteAllAccessTokenOfClient( client );
    }

    @Override
    @Transactional
    public void generateClient( ClientEntity client ) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId( client.getId() );
        clientEntity.setUrl( client.getUrl() );
        clientEntity.setName( client.getName() );
        clientEntity.setUser( client.getUser() );
        clientEntity.setCallback( client.getCallback() );
        clientEntity.setDescription( client.getDescription() );
        clientEntity.setSecret( encoder.encode( client.getSecret() ) );
        clientRepository.persistClient( client );
    }

}
