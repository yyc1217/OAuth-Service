package tw.edu.ncu.cc.oauth.server.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessToken;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.service.ScopeService;

import java.util.Set;

@Component
public class AccessTokenEntity_AccessTokenConverter implements Converter< AccessTokenEntity, AccessToken > {

    private ScopeService scopeService;

    @Autowired
    public void setScopeService( ScopeService scopeService ) {
        this.scopeService = scopeService;
    }

    @Override
    public AccessToken convert( AccessTokenEntity source ) {

        AccessToken accessToken = new AccessToken();
        accessToken.setId( source.getId() + "" );
        accessToken.setUser( source.getUser().getName() );

        Set< String > scope = scopeService.decode( source.getScope() );
        accessToken.setScope( scope.toArray( new String[ scope.size() ] ) );
        accessToken.setLast_updated( source.getDateUpdated() );

        return accessToken;
    }

}
