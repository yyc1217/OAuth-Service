package tw.edu.ncu.cc.oauth.resource.filter

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.resource.exception.InvalidRequestException
import tw.edu.ncu.cc.oauth.resource.service.TokenConfirmService

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static tw.edu.ncu.cc.oauth.resource.config.RequestConfig.*

public class TokenAccessDecisionFilter extends AbstractFilter {

    def boolean isOauthEnable

    def TokenConfirmService tokenConfirmService

    public TokenAccessDecisionFilter() {
        this( false )
    }

    public TokenAccessDecisionFilter( boolean isOauthEnable ) {
        this.isOauthEnable = isOauthEnable
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {

        HttpServletRequest  httpRequest  = ( HttpServletRequest ) request
        HttpServletResponse httpResponse = ( HttpServletResponse ) response

        try {
            checkAuthentication( httpRequest )
            chain.doFilter( request, response )
        } catch ( InvalidRequestException e ) {
            httpResponse.sendError( e.httpStatus.value(), e.message )
        }
    }

    private void checkAuthentication( HttpServletRequest request ) {
        if( isApiRequest( request ) ) {
            if( ! isValidAndBindApiRequest( request ) ) {
                throw new InvalidRequestException( HttpStatus.UNAUTHORIZED, "invalid api token: not exist or reach call limit" )
            }
            if( isOauthEnable && isOAuthRequest( request ) ) {
                trybindOauthAuthentication( request )
            }
        } else {
            throw new InvalidRequestException( HttpStatus.BAD_REQUEST, "api token not provided" )
        }
    }

    private static boolean isApiRequest( HttpServletRequest request ) {
        return request.getHeader( API_TOKEN_HEADER ) != null
    }

    private boolean isValidAndBindApiRequest( HttpServletRequest request ) {
        return tokenConfirmService.readApiToken(
                request.getHeader( API_TOKEN_HEADER )
        ) != null
    }

    private static boolean isOAuthRequest( HttpServletRequest request ) {
        String authorization = request.getHeader( OAUTH_TOKEN_HEADER )
        return authorization != null && authorization.startsWith( OAUTH_TOKEN_PREFIX )
    }

    private void trybindOauthAuthentication( HttpServletRequest request ) {
        String accessTokenString = readAccessToken( request )
        AccessTokenObject token  = tokenConfirmService.readAccessToken( accessTokenString )
        if ( token != null ) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            token.user, accessTokenString, AuthorityUtils.createAuthorityList( token.scope )
                    )
            )
        }
    }

    private static String readAccessToken( HttpServletRequest request ) {
        String authorization = request.getHeader( OAUTH_TOKEN_HEADER )
        return authorization.trim().substring( OAUTH_TOKEN_PREFIX.length() ).trim()
    }

}
