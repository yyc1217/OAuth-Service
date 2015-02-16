package tw.edu.ncu.cc.oauth.server.converter;

import org.springframework.core.convert.converter.Converter;
import tw.edu.ncu.cc.oauth.data.v1.management.application.Application;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;

public class ClientEntity_ApplicationConverter implements Converter< ClientEntity, Application > {

    @Override
    public Application convert( ClientEntity source ) {
        Application application = new Application();
        application.setName( source.getName() );
        application.setDescription( source.getDescription() );
        application.setOwner( source.getOwner().getName() );
        application.setUrl( source.getUrl() );
        application.setCallback( source.getCallback() );
        return application;
    }

}
