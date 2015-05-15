package tw.edu.ncu.cc.oauth.server.concepts.client

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.oauth.server.concepts.security.SecretService

import javax.persistence.metamodel.Attribute

import static org.springframework.data.jpa.domain.Specifications.where

@Service
@CompileStatic
class ClientServiceImpl implements ClientService {

    @Autowired
    def SecretService secretService

    @Autowired
    def ClientRepository clientRepository

    @Override
    Client create( Client client ) {
        String newSecret = secretService.generateToken()
        client.encryptedSecret = secretService.encodeSecret( newSecret )
        clientRepository.save( client )
        client.secret = newSecret
        client
    }

    @Override
    Client refreshSecret( Client client ) {
        String newSecret = secretService.generateToken()
        client.encryptedSecret = secretService.encodeSecret( newSecret )
        clientRepository.save( client )
        client.secret = newSecret
        client
    }

    @Override
    Client update( Client client ) {
        clientRepository.save( client )
    }

    @Override
    Client delete( Client client ) {
        client.deleted = true
        clientRepository.save( client )
    }

    @Override
    Client findUndeletedBySerialId( String serialId, Attribute...attributes = [] ) {
        long id = secretService.decodeHashId( serialId )
        clientRepository.findOne(
                where( ClientSpecifications.idEquals( id as Integer ) )
                        .and( ClientSpecifications.undeleted() )
                        .and( ClientSpecifications.include( attributes ) )
        )
    }

    @Override
    boolean isCredentialValid( String serialId, String secret ) {
        Client client = findUndeletedBySerialId( serialId )
        if( client == null ) {
            return false
        } else {
            return secretService.matchesSecret( secret, client.encryptedSecret )
        }
    }

}
