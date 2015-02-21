package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ncu.cc.oauth.data.v1.management.application.Application;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.helper.StringGenerator;
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository;
import tw.edu.ncu.cc.oauth.server.service.ClientService;
import tw.edu.ncu.cc.oauth.server.service.UserService;

import javax.persistence.NoResultException;

@Service
public class ClientServiceImpl implements ClientService {

    private PasswordEncoder passwordEncoder;
    private ClientRepository clientRepository;
    private UserService userService;

    @Autowired
    public void setPasswordEncoder( PasswordEncoder passwordEncoder ) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService( UserService userService ) {
        this.userService = userService;
    }

    @Autowired
    public void setClientRepository( ClientRepository clientRepository ) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public void revokeClientTokensByID( String id ) {
        clientRepository.revokeClientTokens( readClientByID( id ) );
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public boolean isClientValid( String clientID, String clientSecret ) {
        try {
            return passwordEncoder.matches(
                    clientSecret, readClientByID( clientID ).getSecret()
            );
        } catch ( NoResultException ignore ) {
            return false;
        }
    }

    @Override
    @Transactional
    public ClientEntity createClient( Application application ) {
        ClientEntity client = overrideClientByApplication( new ClientEntity(), application );

        String secret = StringGenerator.generateToken();
        client.setSecret( passwordEncoder.encode( secret ) );
        ClientEntity clientEntity = clientRepository.createClient( client );
        client.setId( clientEntity.getId() );
        client.setSecret( secret );
        return client;
    }

    @Override
    @Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
    public ClientEntity readClientByID( String id ) {
        return clientRepository.readClientByID( Integer.parseInt( id ) );
    }

    @Override
    @Transactional
    public ClientEntity updateClient( String id, Application application ) {
        return clientRepository.updateClient(
                overrideClientByApplication( readClientByID( id ), application )
        );
    }

    @Override
    @Transactional
    public ClientEntity deleteClientByID( String id ) {
        ClientEntity targetClient = readClientByID( id );
        clientRepository.deleteClient( targetClient );
        return targetClient;
    }

    @Override
    @Transactional
    public ClientEntity refreshClientSecret( String id ) {
        ClientEntity client = readClientByID( id );

        String secret = StringGenerator.generateToken();
        client.setSecret( passwordEncoder.encode( secret ) );
        clientRepository.updateClient( client );

        ClientEntity clientEntity = overrideClientByClient( new ClientEntity(), client );
        clientEntity.setSecret( secret );
        return clientEntity;
    }

    private ClientEntity overrideClientByClient( ClientEntity target, ClientEntity source ) {
        target.setId( source.getId() );
        target.setSecret( source.getSecret() );
        target.setUrl( source.getUrl() );
        target.setName( source.getName() );
        target.setOwner( source.getOwner() );
        target.setCallback( source.getCallback() );
        target.setDescription( source.getDescription() );
        target.setDateCreated( source.getDateCreated() );
        target.setDateUpdated( source.getDateUpdated() );
        return target;
    }

    private ClientEntity overrideClientByApplication( ClientEntity client, Application application ) {
        client.setUrl( application.getUrl() );
        client.setName( application.getName() );
        client.setCallback( application.getCallback() );
        client.setDescription( application.getDescription() );
        client.setOwner( userService.readUser( application.getOwner() ) );
        return client;
    }

}
