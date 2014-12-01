package tw.edu.ncu.cc.oauth.server.filter;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.edu.ncu.cc.oauth.server.data.EditableRequest;
import tw.edu.ncu.cc.oauth.server.entity.AuthCodeEntity;
import tw.edu.ncu.cc.oauth.server.service.AuthCodeService;
import tw.edu.ncu.cc.oauth.server.service.ClientService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class OauthTokenExchangeFilter extends AbstractFilter {

    private String filtPath;
    private int codeExpireSeconds;
    private ClientService clientService;
    private AuthCodeService authCodeService;

    public void setFiltPath( String filtPath ) {
        this.filtPath = filtPath;
    }

    public void setCodeExpireSeconds( int codeExpireSeconds ) {
        this.codeExpireSeconds = codeExpireSeconds;
    }

    @Autowired
    public void setClientService( ClientService clientService ) {
        this.clientService = clientService;
    }

    @Autowired
    public void setAuthCodeService( AuthCodeService authCodeService ) {
        this.authCodeService = authCodeService;
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {

        HttpServletRequest httpRequest   = ( HttpServletRequest ) request;
        HttpServletResponse httpResponse = ( HttpServletResponse ) response;
        String requestPath = httpRequest.getRequestURI().substring( httpRequest.getContextPath().length() );

        if( filtPath == null ||  requestPath.startsWith( filtPath ) ) {

            try {
                validate( new EditableRequest( httpRequest ).setParameter( "redirect_uri", "stub" ) );
            } catch ( OAuthSystemException ignore ) {
                httpResponse.sendError( HttpServletResponse.SC_BAD_REQUEST, "oauth system error" );
                return;
            } catch ( OAuthProblemException e ) {
                httpResponse.setHeader( "Content-Type","application/json" );
                httpResponse.setHeader( "Cache-Control","no-store" );
                httpResponse.setHeader( "Pragma","no-cache" );
                httpResponse.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        String.format(
                                "{\"error\":\"%s\",\"error_description\":\"%s\"}",
                                e.getError(),
                                e.getDescription()
                        )
                );
                return;
            }
        }
        chain.doFilter( request, response );
    }

    public void validate( HttpServletRequest httpServletRequest  ) throws OAuthProblemException, OAuthSystemException {

        OAuthTokenRequest oauthRequest = new OAuthTokenRequest( httpServletRequest );
        String grantType = oauthRequest.getGrantType();

        if ( grantType.equals( GrantType.AUTHORIZATION_CODE.toString() ) ){

            Integer clientID    = Integer.valueOf( oauthRequest.getClientId() );
            String clientSecret = oauthRequest.getClientSecret();
            String authCode     = oauthRequest.getCode();

            if ( ! clientService.isClientValid( clientID, clientSecret ) ) {
                throw OAuthProblemException.error(
                        OAuthError.TokenResponse.INVALID_CLIENT, "INVALID CLIENT"
                );
            }

            if( ! isAuthCodeValid( authCode, clientID ) ) {
                throw OAuthProblemException.error(
                        OAuthError.TokenResponse.INVALID_GRANT, "INVALID AUTH CODE"
                );
            }
        } else {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.UNSUPPORTED_GRANT_TYPE, "ONLY SUPPORT AUTHORIZATION CODE"
            );
        }
    }

    private boolean isAuthCodeValid( String authCode, Integer clientID ) {
        AuthCodeEntity code = authCodeService.getAuthCode( authCode );
        if( code == null || ! code.getClient().getId().equals( clientID ) ) {
            return false;
        } else  if( codeExpireSeconds == 0 ) {
            return true;
        } else {
            return code.getDateCreated().after(
                    new Date( System.currentTimeMillis() - codeExpireSeconds * 1000 )
            );
        }
    }

}
