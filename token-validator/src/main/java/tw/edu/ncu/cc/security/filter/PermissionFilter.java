package tw.edu.ncu.cc.security.filter;

import org.apache.oltu.oauth2.common.OAuth;

import javax.annotation.Priority;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;
import java.util.Set;

@Priority( Priorities.AUTHORIZATION )
public class PermissionFilter implements ContainerRequestFilter {

    private final String[] permissions;

    public PermissionFilter( String[] permissions ) {
        this.permissions = ( permissions == null ? new String[]{} : permissions );
    }

    @Override
    public void filter( ContainerRequestContext request ) throws IOException {

        if ( permissions.length > 0 ) {

            String tokenString = request.getHeaderString( OAuth.HeaderType.AUTHORIZATION );

            Set<String> scopes = getScopes( tokenString );

            for ( String permission : permissions ) {
                if ( ! scopes.contains( permission ) ) {
                    throw new ForbiddenException();
                }
            }

        }else{
            throw new ForbiddenException();
        }

    }

    private Set<String> getScopes( String tokenString ) {

        return null;//TODO READ DATA FROM DB
    }

}