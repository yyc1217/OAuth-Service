package tw.edu.ncu.cc.oauth.security.provider;

import org.glassfish.jersey.server.model.AnnotatedMethod;
import tw.edu.ncu.cc.oauth.security.annotation.PermissionRequest;
import tw.edu.ncu.cc.oauth.security.filter.PermissionFilter;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class PermissionVerifier implements DynamicFeature {

    @Override
    public void configure( final ResourceInfo resourceInfo, final FeatureContext context ) {

        AnnotatedMethod method = new AnnotatedMethod( resourceInfo.getResourceMethod() );

        if ( method.isAnnotationPresent( PermissionRequest.class ) ) {
            context.register( new PermissionFilter( method.getAnnotation( PermissionRequest.class ).value() ) );
            return;
        }

        if ( method.isAnnotationPresent( DenyAll.class ) ) {
            context.register( new PermissionFilter( null ) );
            return;
        }

        if ( method.isAnnotationPresent( PermitAll.class ) ) {
            return;
        }

        Class<?> resourceClass = resourceInfo.getResourceClass();
        if ( resourceClass.isAnnotationPresent( PermissionRequest.class ) ) {
            context.register( new PermissionFilter( resourceClass.getAnnotation( PermissionRequest.class ).value() ) );
        }

    }

}
