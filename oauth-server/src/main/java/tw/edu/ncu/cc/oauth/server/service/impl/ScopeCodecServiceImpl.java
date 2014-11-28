package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.edu.ncu.cc.oauth.server.service.PermissionService;
import tw.edu.ncu.cc.oauth.server.service.ScopeCodecService;

import java.util.HashSet;
import java.util.Set;

@Service
public class ScopeCodecServiceImpl implements ScopeCodecService {

    public static final char YES = '1';
    public static final char NO  = '0';

    private PermissionService permissionService;

    @Autowired
    public void setPermissionService( PermissionService permissionService ) {
        this.permissionService = permissionService;
    }

    @Override
    public boolean exist( Set< String > scope ) {
        for ( String permission : scope ) {
            if( permissionService.getPermission( permission ) == null ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String encode( Set< String > scope ) {
        StringBuilder stringBuilder = new StringBuilder( 50 );
        for ( String permission : scope ) {
            int position = permissionService.getPermission( permission ).getId();
            while( position > stringBuilder.length() ) {
                stringBuilder.append( NO + NO + NO + NO + NO );
            }
            stringBuilder.setCharAt( position, YES );
        }
        return stringBuilder.toString();
    }

    @Override
    public Set< String > decode( String scope ) {
        Set< String > scopes = new HashSet<>();
        for( int i = 0; i < scope.length(); i ++ ) {
            if( scope.charAt( i ) == YES ) {
                scopes.add( permissionService.getPermission( i ).getName() );
            }
        }
        return scopes;
    }

}
