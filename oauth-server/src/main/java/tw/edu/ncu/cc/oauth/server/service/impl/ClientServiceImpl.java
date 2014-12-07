package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.server.component.StringGenerator;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository;
import tw.edu.ncu.cc.oauth.server.repository.ClientTokenRepository;
import tw.edu.ncu.cc.oauth.server.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

    private PasswordEncoder passwordEncoder;
    private StringGenerator stringGenerator;
    private ClientRepository clientRepository;
    private ClientTokenRepository clientTokenRepository;

    @Autowired
    public void setPasswordEncoder( PasswordEncoder passwordEncoder ) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setStringGenerator( StringGenerator stringGenerator ) {
        this.stringGenerator = stringGenerator;
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
    public ClientEntity readClient( int clientID ) {
        return clientRepository.readClient( clientID );
    }

    @Override
    @Transactional
    public ClientEntity updateClient( ClientEntity client ) {
        return clientRepository.updateClient( client );
    }

    @Override
    @Transactional
    public ClientEntity deleteClient( ClientEntity client ) {
        clientRepository.deleteClient( client );
        return client;
    }

    @Override
    @Transactional
    public void revokeClientTokens( ClientEntity client ) {
        clientTokenRepository.deleteAllAccessTokenOfClient( client );
    }

    @Override
    @Transactional
    public ClientEntity refreshClientSecret( ClientEntity client ) {
        String secret = stringGenerator.generateToken();
        client.setSecret( passwordEncoder.encode( secret ) );
        updateClient( client );
        client.setSecret( secret );
        return client;
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public boolean isClientValid( int clientID, String clientSecret ) {
        ClientEntity client = readClient( clientID );
        return client != null && passwordEncoder.matches( clientSecret, client.getSecret() );
    }

    @Override
    @Transactional
    public ClientEntity createClient( ClientEntity client ) {
        String secret = stringGenerator.generateToken();
        client.setSecret( passwordEncoder.encode( secret ) );
        ClientEntity clientEntity = clientRepository.createClient( client );
        client.setId( clientEntity.getId() );
        client.setSecret( secret );
        return client;
    }

}