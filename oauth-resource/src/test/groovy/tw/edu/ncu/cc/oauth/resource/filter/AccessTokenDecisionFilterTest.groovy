package tw.edu.ncu.cc.oauth.resource.filter

import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.resource.service.TokenConfirmService

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static tw.edu.ncu.cc.oauth.resource.config.RequestConfig.*

class AccessTokenDecisionFilterTest extends Specification {

    def FilterChain filterChain
    def HttpServletRequest  request
    def HttpServletResponse response
    def TokenConfirmService tokenConfirmService
    def AccessTokenDecisionFilter accessTokenDecisionFilter

    def setup() {
        filterChain = Mock( FilterChain )
        request  = Mock( HttpServletRequest )
        response = Mock( HttpServletResponse )
        tokenConfirmService = Mock( TokenConfirmService )

        accessTokenDecisionFilter = new AccessTokenDecisionFilter()
        accessTokenDecisionFilter.tokenConfirmService = tokenConfirmService
    }

    def "it should response with 400 directly if no access token appended"() {
        given:
            request.getHeader( ACCESS_TOKEN_HEADER ) >> null
        when:
            accessTokenDecisionFilter.doFilter( request, response, filterChain )
        then:
            1 * response.sendError( 400, _ as String )
        and:
            0 * filterChain.doFilter( _ as HttpServletRequest, _ as HttpServletResponse )
    }

    def "it should response 401 if directly if access token provided but invalid"() {
        given:
            request.getHeader( ACCESS_TOKEN_HEADER ) >> ACCESS_TOKEN_PREFIX + " " + "ACCESS_TOKEN1"
        and:
            tokenConfirmService.readApiToken( "ACCESS_TOKEN2" ) >> new AccessTokenObject()
        when:
            accessTokenDecisionFilter.doFilter( request, response, filterChain )
        then:
            1 * response.sendError( 401, _ as String )
        and:
            0 * filterChain.doFilter( _ as HttpServletRequest, _ as HttpServletResponse )
    }

    def "it should hold access token as authentication in spring security context"() {
        given:
            request.getHeader( ACCESS_TOKEN_HEADER ) >> ACCESS_TOKEN_PREFIX + " " + "ACCESS_TOKEN"
        and:
            tokenConfirmService.readAccessToken( "ACCESS_TOKEN" ) >> new AccessTokenObject(
                    user: "USER",
                    scope: [ "PERMISSION" ]
            )
        when:
            accessTokenDecisionFilter.doFilter( request, response, filterChain )
        then:
            SecurityContextHolder.getContext().getAuthentication().getAuthorities().size() == 1
        and:
            1 * request.setAttribute( ACCESS_TOKEN_ATTR, _ )
        and:
            1 * filterChain.doFilter( _ as HttpServletRequest, _ as HttpServletResponse )
    }

}
