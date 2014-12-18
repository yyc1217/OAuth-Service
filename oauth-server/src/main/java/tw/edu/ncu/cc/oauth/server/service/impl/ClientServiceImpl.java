package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.data.v1.management.application.Application;
import tw.edu.ncu.cc.oauth.server.component.StringGenerator;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository;
import tw.edu.ncu.cc.oauth.server.service.ClientService;
import tw.edu.ncu.cc.oauth.server.service.UserService;

import javax.persistence.NoResultException;

@Service
public class ClientServiceImpl implements ClientService {

    private PasswordEncoder passwordEncoder;
    private StringGenerator stringGenerator;
    private ClientRepository clientRepository;
    private UserService userService;

    @Autowired
    public void setUserService( UserService userService ) {
        this.userService = userService;
    }

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

    @Override
    @Transactional
    public void revokeClientTokens( String id ) {
        clientRepository.revokeClientTokens( readClient( id ) );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public boolean isClientValid( String clientID, String clientSecret ) {
        try {
            return passwordEncoder.matches( clientSecret, readClient( clientID ).getSecret() );
        } catch ( NoResultException ignore ) {
            return false;
        }
    }

    @Override
    @Transactional
    public ClientEntity createClient( Application application ) {
        ClientEntity client = buildClientEntity( application );

        String secret = stringGenerator.generateToken();
        client.setSecret( passwordEncoder.encode( secret ) );
        ClientEntity clientEntity = clientRepository.createClient( client );
        client.setId( clientEntity.getId() );
        client.setSecret( secret );
        return client;
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public ClientEntity readClient( String id ) {
        return clientRepository.readClient( Integer.parseInt( id ) );
    }

    @Override
    @Transactional
    public ClientEntity updateClient( String id, Application application ) {
        ClientEntity targetClient = readClient( id );
        targetClient.setUrl( application.getUrl() );
        targetClient.setName( application.getName() );
        targetClient.setCallback( application.getCallback() );
        targetClient.setDescription( application.getDescription() );
        targetClient.setOwner( userService.readUser( application.getOwner() ) );
        return clientRepository.updateClient( targetClient );
    }

    @Override
    @Transactional
    public ClientEntity deleteClient( String id ) {
        ClientEntity targetClient = readClient( id );
        clientRepository.deleteClient( targetClient );
        return targetClient;
    }

    @Override
    @Transactional
    public ClientEntity refreshClientSecret( String id ) {
        ClientEntity client = readClient( id );

        String secret = stringGenerator.generateToken();
        client.setSecret( passwordEncoder.encode( secret ) );
        clientRepository.updateClient( client );
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId( client.getId() );
        clientEntity.setSecret( secret );
        clientEntity.setUrl( client.getUrl() );
        clientEntity.setName( client.getName() );
        clientEntity.setOwner( client.getOwner() );
        clientEntity.setCallback( client.getCallback() );
        clientEntity.setDescription( client.getDescription() );
        clientEntity.setDateCreated( client.getDateCreated() );
        clientEntity.setDateUpdated( client.getDateUpdated() );
        return clientEntity;
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
