package tw.edu.ncu.cc.oauth.resource.filter

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.resource.core.ApiCredentialHolder
import tw.edu.ncu.cc.oauth.resource.exception.InvalidRequestException
import tw.edu.ncu.cc.oauth.resource.service.TokenConfirmService

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static tw.edu.ncu.cc.oauth.resource.config.RequestConfig.*

public class AccessTokenDecisionFilter extends AbstractFilter {

    def TokenConfirmService tokenConfirmService

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
        if( isOAuthRequest( request ) ) {
            AccessTokenObject accessTokenObject = findAccessToken( request )
            if( accessTokenObject != null ) {
                bindAccessToken( request, accessTokenObject )
            } else {
                throw new InvalidRequestException( HttpStatus.UNAUTHORIZED, "invalid access token" )
            }
        } else {
            throw new InvalidRequestException( HttpStatus.BAD_REQUEST, "access token not provided" )
        }
    }

    private static boolean isOAuthRequest( HttpServletRequest request ) {
        String authorization = request.getHeader( ACCESS_TOKEN_HEADER )
        return authorization != null && authorization.startsWith( ACCESS_TOKEN_PREFIX )
    }

    private AccessTokenObject findAccessToken( HttpServletRequest request ) {
        String accessToken = readAccessTokenFromRequest( request )
        if( ApiCredentialHolder.containsAccessToken( accessToken ) ) {
            ApiCredentialHolder.getAccessToken( accessToken )
        } else {
            tokenConfirmService.readAccessToken( accessToken )
        }
    }

    private static String readAccessTokenFromRequest( HttpServletRequest request ) {
        String authorization = request.getHeader( ACCESS_TOKEN_HEADER )
        return authorization.trim().substring( ACCESS_TOKEN_PREFIX.length() ).trim()
    }

    private static void bindAccessToken( HttpServletRequest request, AccessTokenObject accessTokenObject ) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        accessTokenObject.user, "", AuthorityUtils.createAuthorityList( accessTokenObject.scope )
                )
        )
        request.setAttribute( ACCESS_TOKEN_ATTR, accessTokenObject )
    }

}
