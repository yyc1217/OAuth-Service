package tw.edu.ncu.cc.oauth.server.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tw.edu.ncu.cc.oauth.data.v1.management.application.IdApplication;
import tw.edu.ncu.cc.oauth.data.v1.management.token.AppAccessToken;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.entity.ClientEntity;
import tw.edu.ncu.cc.oauth.server.service.ScopeCodecService;

import java.util.Set;

@Component
public class AppAccessTokenConverter implements Converter< AccessTokenEntity, AppAccessToken > {

    private ScopeCodecService scopeCodecService;

    @Autowired
    public void setScopeCodecService( ScopeCodecService scopeCodecService ) {
        this.scopeCodecService = scopeCodecService;
    }

    @Override
    public AppAccessToken convert( AccessTokenEntity source ) {

        ClientEntity client = source.getClient();

        IdApplication application = new IdApplication();
        application.setId( client.getId() + "" );
        application.setName( client.getName() );
        application.setDescription( client.getDescription() );
        application.setOwner( client.getOwner().getName() );
        application.setUrl( client.getUrl() );
        application.setCallback( client.getCallback() );

        AppAccessToken accessToken = new AppAccessToken();
        accessToken.setId( source.getId() + "" );
        accessToken.setUser( source.getUser().getName() );
        accessToken.setApplication( application );

        Set< String > scope = scopeCodecService.decode( source.getScope() );
        accessToken.setScope( scope.toArray( new String[ scope.size() ] ) );
        accessToken.setLast_updated( source.getDateUpdated() );

        return accessToken;
    }

}
