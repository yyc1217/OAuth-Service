package tw.edu.ncu.cc.oauth.server.converter;

import org.springframework.core.convert.converter.Converter;
import tw.edu.ncu.cc.oauth.data.v1.management.application.SecretIdApplication;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;

public class ClientEntity_SecretIdApplicationConverter implements Converter< ClientEntity, SecretIdApplication > {

    @Override
    public SecretIdApplication convert( ClientEntity source ) {
        SecretIdApplication application = new SecretIdApplication();
        application.setId( source.getId() + "" );
        application.setSecret( source.getSecret() );
        application.setName( source.getName() );
        application.setDescription( source.getDescription() );
        application.setOwner( source.getOwner().getName() );
        application.setUrl( source.getUrl() );
        application.setCallback( source.getCallback() );
        return application;
    }

}
