package tw.edu.ncu.cc.oauth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.edu.ncu.cc.oauth.server.service.PermissionService;
import tw.edu.ncu.cc.oauth.server.service.ScopeService;

import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ScopeServiceImpl implements ScopeService {

    public static final char YES  = '1';
    public static final String NO = "00000";

    private PermissionService permissionService;

    @Autowired
    public void setPermissionService( PermissionService permissionService ) {
        this.permissionService = permissionService;
    }

    @Override
    public boolean exist( Set< String > scope ) {
        try {
            for ( String permission : scope ) {
                permissionService.readPermission( permission );
            }
            return true;
        } catch ( NoResultException ignore ) {
            return false;
        }
    }

    @Override
    public String encode( Set< String > scope ) {
        StringBuilder stringBuilder = new StringBuilder( 50 );
        for ( String permission : scope ) {
            int position = permissionService.readPermission( permission ).getId();
            while( position > stringBuilder.length() ) {
                stringBuilder.append( NO );
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
                scopes.add( permissionService.readPermission( i ).getName() );
            }
        }
        return scopes;
    }

}
