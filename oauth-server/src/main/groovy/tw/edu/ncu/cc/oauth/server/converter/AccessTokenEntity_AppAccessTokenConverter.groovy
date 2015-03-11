package tw.edu.ncu.cc.oauth.server.converter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClient
import tw.edu.ncu.cc.oauth.data.v1.management.token.ClientAccessToken
import tw.edu.ncu.cc.oauth.server.domain.AccessTokenEntity
import tw.edu.ncu.cc.oauth.server.domain.ClientEntity
import tw.edu.ncu.cc.oauth.server.service.ScopeService

@Component
public class AccessTokenEntity_AppAccessTokenConverter implements Converter< AccessTokenEntity, ClientAccessToken > {

    private ScopeService scopeService;

    @Autowired
    public void setScopeService( ScopeService scopeService ) {
        this.scopeService = scopeService;
    }

    @Override
    public ClientAccessToken convert( AccessTokenEntity source ) {

        ClientEntity client = source.getClient();

        IdClient application = new IdClient();
        application.setId( client.getId().toString() );
        application.setName( client.getName() );
        application.setDescription( client.getDescription() );
        application.setOwner( client.getOwner().getName() );
        application.setUrl( client.getUrl() );
        application.setCallback( client.getCallback() );

        ClientAccessToken accessToken = new ClientAccessToken();
        accessToken.setId( source.getId().toString() );
        accessToken.setUser( source.getUser().getName() );
        accessToken.setApplication( application );

        Set< String > scope = scopeService.decode( source.getScope() );
        accessToken.setScope( scope.toArray( new String[ scope.size() ] ) );
        accessToken.setLast_updated( source.getDateUpdated() );

        return accessToken;
    }

}
