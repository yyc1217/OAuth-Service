package tw.edu.ncu.cc.oauth.server.filter;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tw.edu.ncu.cc.oauth.server.helper.OAuthProblemBuilder;
import tw.edu.ncu.cc.oauth.server.repository.ClientRepository;
import tw.edu.ncu.cc.oauth.server.service.ScopeCodecService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class OauthAuthorizationFilter extends AbstractFilter {

    private String filtPath;
    private ScopeCodecService scopeCodecService;
    private ClientRepository clientRepository;

    public void setFiltPath( String filtPath ) {
        this.filtPath = filtPath;
    }

    @Autowired
    public void setScopeCodec( ScopeCodecService scopeCodecService ) {
        this.scopeCodecService = scopeCodecService;
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
            } catch ( OAuthSystemException ignore ) {
                return;
            } catch ( OAuthProblemException e ) {
                if( e.getRedirectUri() == null ) {
                    httpResponse.sendError( HttpServletResponse.SC_BAD_REQUEST, e.getMessage() );
                } else if( e.getState() == null ) {
                    httpResponse.sendRedirect(
                            String.format(
                                    "%s?error=%s&error_description=%s",
                                    e.getRedirectUri(),
                                    e.getError(),
                                    e.getDescription()
                            )
                    );
                } else {
                    httpResponse.sendRedirect(
                            String.format(
                                    "%s?error=%s&error_description=%s&state=%s",
                                    e.getRedirectUri(),
                                    e.getError(),
                                    e.getDescription(),
                                    e.getState()
                            )
                    );
                }
                return;
            }
        }
        chain.doFilter( request, response );
    }

    public void validate( HttpServletRequest httpServletRequest  ) throws OAuthSystemException, OAuthProblemException {

        OAuthAuthzRequest oauthRequest =  new OAuthAuthzRequest( httpServletRequest );
        Set<String> scope  = oauthRequest.getScopes();
        String clientState = oauthRequest.getState();
        String clientIDS   = oauthRequest.getClientId();
        int clientID = Integer.parseInt( clientIDS );

        if ( clientRepository.getClient( clientID )  == null ) {
            throw OAuthProblemBuilder
                    .error( OAuthError.CodeResponse.INVALID_REQUEST )
                    .description( "CLIENT NOT EXIST" )
                    .state( clientState )
                    .build();
        }

        String callback = clientRepository.getClient( clientID ).getCallback();

        if ( StringUtils.isEmpty( clientState ) ) {
            throw OAuthProblemBuilder
                    .error( OAuthError.CodeResponse.INVALID_REQUEST )
                    .description( "STATE NOT PROVIDED" )
                    .redirectUri( callback )
                    .state( clientState )
                    .build();
        }
        if ( ! scopeCodecService.exist( scope ) ) {
            throw OAuthProblemBuilder
                    .error( OAuthError.CodeResponse.INVALID_SCOPE )
                    .description( "PERMISSION NOT EXISTS" )
                    .redirectUri( callback )
                    .state( clientState )
                    .build();
        }
    }


}
