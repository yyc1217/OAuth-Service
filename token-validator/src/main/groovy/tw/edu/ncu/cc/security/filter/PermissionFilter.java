package tw.edu.ncu.cc.security.filter;

import com.google.gson.Gson;
import org.apache.oltu.oauth2.common.OAuth;

import javax.annotation.Priority;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Priority( Priorities.AUTHORIZATION )
public class PermissionFilter implements ContainerRequestFilter {

    private final String[] requiredPermissions;

    public PermissionFilter( String[] requiredPermissions ) {
        this.requiredPermissions = ( requiredPermissions == null ? new String[]{} : requiredPermissions );
    }

    @Override
    public void filter( ContainerRequestContext request ) throws IOException {

        if ( requiredPermissions.length > 0 ) {

            String tokenString = request.getHeaderString( OAuth.HeaderType.AUTHORIZATION );
            Set<String> scopes = getScopes( tokenString );

            for ( String requiredPermission : requiredPermissions ) {
                if ( ! scopes.contains( requiredPermission ) ) {
                    if( requiredPermission.contains( ":" ) ) {
                        String prefix = requiredPermission.split( ":" )[0];
                        if( ! scopes.contains( prefix ) ) {
                            throw new ForbiddenException();
                        }
                    }else {
                        throw new ForbiddenException();
                    }
                }
            }

        }else{
            throw new ForbiddenException();
        }

    }

    private Set<String> getScopes( String tokenString ) {
        AccessToken accessToken = new Gson().fromJson( "", AccessToken.class );
        return new HashSet<>( accessToken.getScopes() );
    }

}