package tw.edu.ncu.cc.oauth.resource.filter

import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject
import tw.edu.ncu.cc.oauth.resource.service.TokenConfirmService

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static tw.edu.ncu.cc.oauth.resource.config.RequestConfig.getAPI_TOKEN_ATTR
import static tw.edu.ncu.cc.oauth.resource.config.RequestConfig.getAPI_TOKEN_HEADER

class ApiTokenDecisionFilterTest extends Specification {

    def FilterChain filterChain
    def HttpServletRequest  request
    def HttpServletResponse response
    def TokenConfirmService tokenConfirmService
    def ApiTokenDecisionFilter apiTokenDecisionFilter

    def setup() {
        filterChain = Mock( FilterChain )
        request  = Mock( HttpServletRequest )
        response = Mock( HttpServletResponse )
        tokenConfirmService = Mock( TokenConfirmService )

        apiTokenDecisionFilter = new ApiTokenDecisionFilter()
        apiTokenDecisionFilter.tokenConfirmService = tokenConfirmService
    }

    def "it should response with 400 directly if no api token appended"() {
        given:
            request.getHeader( API_TOKEN_HEADER ) >> null
        when:
            apiTokenDecisionFilter.doFilter( request, response, filterChain )
        then:
            1 * response.sendError( 400, _ as String )
        and:
            0 * filterChain.doFilter( _ as HttpServletRequest, _ as HttpServletResponse )
    }

    def "it should response 401 if directly if api token provided but invalid"() {
        given:
            request.getHeader( API_TOKEN_HEADER ) >> "TOKEN1"
        and:
            tokenConfirmService.readApiToken( "TOKEN2" ) >> new ApiTokenObject()
        when:
            apiTokenDecisionFilter.doFilter( request, response, filterChain )
        then:
            1 * response.sendError( 401, _ as String )
        and:
            0 * filterChain.doFilter( _ as HttpServletRequest, _ as HttpServletResponse )
    }

    def "it should hold api token if valid"() {
        given:
            request.getHeader( API_TOKEN_HEADER ) >> "TOKEN"
        and:
            tokenConfirmService.readApiToken( "TOKEN" ) >> new ApiTokenObject(
                    clientId: "testapp"
            )
        when:
            apiTokenDecisionFilter.doFilter( request, response, filterChain )
        then:
            SecurityContextHolder.getContext().getAuthentication().name == "testapp"
        and:
            1 * request.setAttribute( API_TOKEN_ATTR, _ )
        and:
            1 * filterChain.doFilter( _ as HttpServletRequest, _ as HttpServletResponse )
    }

}
