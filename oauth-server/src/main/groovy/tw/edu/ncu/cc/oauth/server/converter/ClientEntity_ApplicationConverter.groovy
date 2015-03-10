package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.client.Client
import tw.edu.ncu.cc.oauth.server.domain.ClientEntity

@Component
public class ClientEntity_ApplicationConverter implements Converter< ClientEntity, Client > {

    @Override
    public Client convert( ClientEntity source ) {
        Client client = new Client();
        client.setName( source.getName() );
        client.setDescription( source.getDescription() );
        client.setOwner( source.getOwner().getName() );
        client.setUrl( source.getUrl() );
        client.setCallback( source.getCallback() );
        return client;
    }

}
