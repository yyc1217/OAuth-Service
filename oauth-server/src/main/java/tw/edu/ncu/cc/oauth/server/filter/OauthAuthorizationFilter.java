package tw.edu.ncu.cc.oauth.server.filter;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
            } catch ( Exception e ) {
                httpResponse.sendError( HttpServletResponse.SC_BAD_REQUEST, e.getMessage() );
                return;
            }
        }
        chain.doFilter( request, response );
    }

    public void validate( HttpServletRequest httpServletRequest  ) throws Exception {

        OAuthAuthzRequest oauthRequest =  new OAuthAuthzRequest( httpServletRequest );
        Set<String> scope   = oauthRequest.getScopes();
        String responseType = oauthRequest.getResponseType();
        String clientState  = oauthRequest.getState();
        String clientID     = oauthRequest.getClientId();

        if ( StringUtils.isEmpty( clientState ) ) {
            throw OAuthProblemException.error(
                    OAuthError.CodeResponse.INVALID_REQUEST, "STATE NOT PROVIDED"
            );
        }
        if ( ! responseType.equals( ResponseType.CODE.toString() ) ) {
            throw OAuthProblemException.error(
                    OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE, "ONLY SUPPORT AUTH CODE"
            );
        }
        if ( clientRepository.getClient( Integer.parseInt( clientID ) ) == null ) {
            throw OAuthProblemException.error(
                    OAuthError.CodeResponse.UNAUTHORIZED_CLIENT, "CLIENT NOT EXISTS"
            );
        }
        if ( ! scopeCodecService.exist( scope ) ) {
            throw OAuthProblemException.error(
                    OAuthError.CodeResponse.INVALID_SCOPE, "PERMISSION NOT EXISTS"
            );
        }
    }


}
