package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClient
import tw.edu.ncu.cc.oauth.server.domain.ClientEntity

@Component
public class ClientEntity_IdApplicationConverter implements Converter< ClientEntity, IdClient > {

    @Override
    public IdClient convert( ClientEntity source ) {
        IdClient application = new IdClient();
        application.setId( source.getId().toString() );
        application.setName( source.getName() );
        application.setDescription( source.getDescription() );
        application.setOwner( source.getOwner().getName() );
        application.setUrl( source.getUrl() );
        application.setCallback( source.getCallback() );
        return application;
    }

}
