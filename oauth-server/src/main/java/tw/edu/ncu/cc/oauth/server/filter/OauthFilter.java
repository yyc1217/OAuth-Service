package tw.edu.ncu.cc.oauth.server.filter;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tw.edu.ncu.cc.oauth.server.component.PermissionCodec;
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class OauthFilter extends AbstractFilter {

    private String filtPath;
    private PermissionCodec permissionCodec;
    private ClientRepository clientRepository;

    public void setFiltPath( String filtPath ) {
        this.filtPath = filtPath;
    }

    @Autowired
    public void setPermissionCodec( PermissionCodec permissionCodec ) {
        this.permissionCodec = permissionCodec;
    }

    @Autowired
    public void setClientRepository( ClientRepository clientRepository ) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {

        HttpServletRequest  httpRequest  = ( HttpServletRequest ) request;
        HttpServletResponse httpResponse = ( HttpServletResponse ) response;
        String requestPath = httpRequest.getRequestURI().substring( httpRequest.getContextPath().length() );

        if( filtPath == null ||  requestPath.startsWith( filtPath ) ) {
            try {
                validate( httpRequest );
            } catch ( Exception e ) {
                httpResponse.sendError( HttpServletResponse.SC_BAD_REQUEST, "OAUTH : " + e.getMessage() );
                return;
            }
        }
        chain.doFilter( request, response );
    }

    public void validate( HttpServletRequest httpServletRequest  ) throws Exception {

        try {

            OAuthAuthzRequest oauthRequest =  new OAuthAuthzRequest( httpServletRequest );
            Set<String> scope   = oauthRequest.getScopes();
            String responseType = oauthRequest.getResponseType();
            String clientState  = oauthRequest.getState();
            String clientID     = oauthRequest.getClientId();

            if ( StringUtils.isEmpty( clientState ) ) {
                throw new Exception( "STATE IS NOT PROVIDED" );
            }
            if ( ! responseType.equals( ResponseType.CODE.toString() ) ) {
                throw new Exception( "ONLY SUPPORT AUTH CODE" );
            }
            if ( clientRepository.getClient( Integer.parseInt( clientID ) ) == null ) {
                throw new Exception( "CLIENT NOT EXISTS" );
            }
            if ( ! permissionCodec.isValid( scope ) ) {
                throw new Exception( "PERMISSION NOT EXISTS" );
            }

        } catch ( OAuthProblemException | OAuthSystemException e ) {
            throw new Exception( e.getMessage() );
        }
    }


}
