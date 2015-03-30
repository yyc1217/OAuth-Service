package tw.edu.ncu.cc.oauth.resource.filter

import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject
import tw.edu.ncu.cc.oauth.resource.service.TokenConfirmService

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

class TokenAccessDecisionFilterTestWithOauth extends Specification {

    def HttpSession session
    def FilterChain filterChain
    def HttpServletRequest  request
    def HttpServletResponse response
    def TokenConfirmService tokenConfirmService
    def TokenAccessDecisionFilter tokenAccessDecisionFilter

    def setup() {
        session = Mock( HttpSession )
        filterChain = Mock( FilterChain )
        request  = Mock( HttpServletRequest )
        response = Mock( HttpServletResponse )
        tokenConfirmService = Mock( TokenConfirmService )

        tokenAccessDecisionFilter = new TokenAccessDecisionFilter( true )
        tokenAccessDecisionFilter.tokenConfirmService = tokenConfirmService
    }

    def "it should response with 400 directly if no api token appended"() {
        given:
            request.getHeader( "X-NCU-API-TOKEN" ) >> null
        when:
            tokenAccessDecisionFilter.doFilter( request, response, filterChain )
        then:
            1 * response.sendError( 400, _ as String )
        and:
            0 * filterChain.doFilter( _ as HttpServletRequest, _ as HttpServletResponse )
    }

    def "it should response 401 if directly if api token provided but invalid"() {
        given:
            request.getHeader( "X-NCU-API-TOKEN" ) >> "API_TOKEN1"
        and:
            tokenConfirmService.readApiToken( "API_TOKEN2" ) >> new ApiTokenObject()
        when:
            tokenAccessDecisionFilter.doFilter( request, response, filterChain )
        then:
            1 * response.sendError( 401, _ as String )
        and:
            0 * filterChain.doFilter( _ as HttpServletRequest, _ as HttpServletResponse )
    }

    def "it should hold api token in session if valid"() {
        given:
            request.getSession() >> session
            request.getHeader( "X-NCU-API-TOKEN" ) >> "API_TOKEN"
        and:
            tokenConfirmService.readApiToken( "API_TOKEN" ) >> new ApiTokenObject()
        when:
            tokenAccessDecisionFilter.doFilter( request, response, filterChain )
        then:
            1 * session.setAttribute( "api_token", _ as ApiTokenObject )
        and:
            1 * filterChain.doFilter( _ as HttpServletRequest, _ as HttpServletResponse )
    }

    def "it should hold access token as authentication in spring security context"() {
        given:
            request.getSession() >> session
            request.getHeader( "X-NCU-API-TOKEN" ) >> "API_TOKEN"
            request.getHeader( "Authorization" ) >> "Bearer ACCESS_TOKEN"
        and:
            tokenConfirmService.readApiToken( "API_TOKEN" ) >> new ApiTokenObject()
        and:
            tokenConfirmService.readAccessToken( "ACCESS_TOKEN" ) >> new AccessTokenObject(
                    user: "USER",
                    scope: [ "PERMISSION" ]
            )
        when:
            tokenAccessDecisionFilter.doFilter( request, response, filterChain )
        then:
            SecurityContextHolder.getContext().getAuthentication() != null
        and:
            1 * filterChain.doFilter( _ as HttpServletRequest, _ as HttpServletResponse )
    }

}
