package tw.edu.ncu.cc.oauth.server.filter;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import tw.edu.ncu.cc.oauth.server.helper.data.EditableRequest;
import tw.edu.ncu.cc.oauth.server.security.OauthTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.oltu.oauth2.common.message.types.GrantType.AUTHORIZATION_CODE;
import static org.apache.oltu.oauth2.common.message.types.GrantType.REFRESH_TOKEN;

public class OauthTokenExchangeFilter extends AbstractFilter {

    private String filtPath = "/oauth/token";
    private OauthTokenService refreshTokenService;
    private OauthTokenService authorizationCodeService;

    public void setRefreshTokenService( OauthTokenService refreshTokenService ) {
        this.refreshTokenService = refreshTokenService;
    }

    public void setAuthorizationCodeService( OauthTokenService authorizationCodeService ) {
        this.authorizationCodeService = authorizationCodeService;
    }

    public void setFiltPath( String filtPath ) {
        this.filtPath = filtPath;
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {

        HttpServletRequest  httpRequest  = ( HttpServletRequest ) request;
        HttpServletResponse httpResponse = ( HttpServletResponse ) response;
        HttpServletRequest stubHttpRequest = new EditableRequest( httpRequest ).setParameter( "redirect_uri", "stub" ); //OLTU BUG

        String requestPath = httpRequest.getRequestURI().substring( httpRequest.getContextPath().length() );

        if( filtPath == null ||  requestPath.startsWith( filtPath ) ) {
            if( ! httpRequest.getMethod().equals( "POST" ) ) {
                responseInvalidMethod( httpResponse );
            } else {
                try {

                    OAuthTokenRequest tokenRequest = new OAuthTokenRequest( stubHttpRequest );
                    OauthTokenService tokenService = decideTokenService( tokenRequest );

                    tokenService.validate( tokenRequest );
                    responseMessage( httpResponse, tokenService.createResponseString( tokenRequest ) );

                } catch ( OAuthSystemException ignore ) {
                    responseRequestError( httpResponse, OAuthProblemException.error(
                            OAuthError.CodeResponse.SERVER_ERROR
                    ) );
                } catch ( OAuthProblemException e ) {
                    responseRequestError( httpResponse, e );
                }
            }
        } else {
            chain.doFilter( httpRequest, response );
        }
    }

    private void responseInvalidMethod( HttpServletResponse httpResponse ) throws IOException {
        httpResponse.sendError( HttpServletResponse.SC_METHOD_NOT_ALLOWED, "only accept post" );
    }

    private OauthTokenService decideTokenService( OAuthTokenRequest tokenRequest ) throws OAuthProblemException {
        String grantType = tokenRequest.getGrantType();
        if( grantType.equals(  AUTHORIZATION_CODE.toString() ) && authorizationCodeService != null ) {
            return authorizationCodeService;
        } else if( grantType.equals( REFRESH_TOKEN.toString() ) && refreshTokenService != null ) {
            return refreshTokenService;
        } else {
            throw OAuthProblemException.error(
                    OAuthError.TokenResponse.UNSUPPORTED_GRANT_TYPE
            );
        }
    }

    private void responseMessage( HttpServletResponse httpResponse, String msg ) throws IOException {
        httpResponse.setHeader( "Content-Type","application/json" );
        httpResponse.getWriter().write( msg );
    }

    private void responseRequestError( HttpServletResponse httpResponse, OAuthProblemException e  ) throws IOException {
        httpResponse.setHeader( "Content-Type","application/json" );
        httpResponse.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                String.format(
                        "{\"error\":\"%s\",\"error_description\":\"%s\"}",
                        e.getError(),
                        e.getDescription()
                )
        );
    }

}
