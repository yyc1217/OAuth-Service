package tw.edu.ncu.cc.oauth.resource.filter

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject
import tw.edu.ncu.cc.oauth.resource.core.ApiCredentialHolder
import tw.edu.ncu.cc.oauth.resource.exception.InvalidRequestException
import tw.edu.ncu.cc.oauth.resource.service.TokenConfirmService

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static tw.edu.ncu.cc.oauth.resource.config.RequestConfig.API_TOKEN_ATTR
import static tw.edu.ncu.cc.oauth.resource.config.RequestConfig.API_TOKEN_HEADER

public class ApiTokenDecisionFilter extends AbstractFilter {

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
        if( isApiRequest( request ) ) {
            ApiTokenObject apiToken = findApiToken( request )
            if( apiToken != null ) {
                bindApiToken( request, apiToken )
            } else {
                throw new InvalidRequestException( HttpStatus.UNAUTHORIZED, "invalid api token: not exist or reach call limit" )
            }
        } else {
            throw new InvalidRequestException( HttpStatus.BAD_REQUEST, "api token not provided" )
        }
    }

    private static boolean isApiRequest( HttpServletRequest request ) {
        return request.getHeader( API_TOKEN_HEADER ) != null
    }

    private ApiTokenObject findApiToken( HttpServletRequest request ) {
        String apiToken = readApiTokenFromRequest( request )
        if( ApiCredentialHolder.containsApiToken( apiToken ) ) {
            ApiCredentialHolder.getApiToken( apiToken )
        } else {
            tokenConfirmService.readApiToken( apiToken )
        }
    }

    private static String readApiTokenFromRequest( HttpServletRequest request ) {
        request.getHeader( API_TOKEN_HEADER )
    }

    private static void bindApiToken( HttpServletRequest request, ApiTokenObject apiTokenObject ) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken( apiTokenObject.clientId, "" )
        )
        request.setAttribute( API_TOKEN_ATTR, apiTokenObject )
    }

}
