package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.client.SecretIdClient
import tw.edu.ncu.cc.oauth.server.domain.ClientEntity

@Component
public class ClientEntity_SecretIdApplicationConverter implements Converter< ClientEntity, SecretIdClient > {

    @Override
    public SecretIdClient convert( ClientEntity source ) {
        SecretIdClient application = new SecretIdClient();
        application.setId( source.getId().toString() );
        application.setSecret( source.getSecret() );
        application.setName( source.getName() );
        application.setDescription( source.getDescription() );
        application.setOwner( source.getOwner().getName() );
        application.setUrl( source.getUrl() );
        application.setCallback( source.getCallback() );
        return application;
    }

}
