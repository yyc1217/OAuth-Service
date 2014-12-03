package tw.edu.ncu.cc.oauth.server.converter;

import org.springframework.core.convert.converter.Converter;
import tw.edu.ncu.cc.oauth.data.v1.management.application.IdApplication;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;

public class IdApplicationConverter implements Converter< ClientEntity, IdApplication > {

    @Override
    public IdApplication convert( ClientEntity source ) {
        IdApplication application = new IdApplication();
        application.setId( source.getId() + "" );
        application.setName( source.getName() );
        application.setDescription( source.getDescription() );
        application.setOwner( source.getOwner().getName() );
        application.setUrl( source.getUrl() );
        application.setCallback( source.getCallback() );
        return application;
    }

}
