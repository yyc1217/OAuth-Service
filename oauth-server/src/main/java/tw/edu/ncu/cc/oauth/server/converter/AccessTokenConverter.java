package tw.edu.ncu.cc.oauth.server.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessToken;
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity;
import tw.edu.ncu.cc.oauth.server.service.ScopeCodecService;

import java.util.Set;

@Component
public class AccessTokenConverter implements Converter< AccessTokenEntity, AccessToken > {

    private ScopeCodecService scopeCodecService;

    @Autowired
    public void setScopeCodecService( ScopeCodecService scopeCodecService ) {
        this.scopeCodecService = scopeCodecService;
    }

    @Override
    public AccessToken convert( AccessTokenEntity source ) {

        AccessToken accessToken = new AccessToken();
        accessToken.setId( source.getId() + "" );
        accessToken.setUser( source.getUser().getName() );

        Set< String > scope = scopeCodecService.decode( source.getScope() );
        accessToken.setScope( scope.toArray( new String[ scope.size() ] ) );
        accessToken.setLast_updated( source.getDateUpdated() );

        return accessToken;
    }

}
