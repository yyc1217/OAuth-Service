package tw.edu.ncu.cc.oauth.resource.filter

import spock.lang.Specification
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject
import tw.edu.ncu.cc.oauth.resource.service.TokenConfirmService

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static tw.edu.ncu.cc.oauth.resource.config.RequestConfig.getAPI_TOKEN_HEADER

class TokenAccessDecisionFilterTestWithoutOauth extends Specification {

    def FilterChain filterChain
    def HttpServletRequest  request
    def HttpServletResponse response
    def TokenConfirmService tokenConfirmService
    def TokenAccessDecisionFilter tokenAccessDecisionFilter

    def setup() {
        filterChain = Mock( FilterChain )
        request  = Mock( HttpServletRequest )
        response = Mock( HttpServletResponse )
        tokenConfirmService = Mock( TokenConfirmService )

        tokenAccessDecisionFilter = new TokenAccessDecisionFilter()
        tokenAccessDecisionFilter.tokenConfirmService = tokenConfirmService
    }

    def "it should response with 400 directly if no api token appended"() {
        given:
            request.getHeader( API_TOKEN_HEADER ) >> null
        when:
            tokenAccessDecisionFilter.doFilter( request, response, filterChain )
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
            tokenAccessDecisionFilter.doFilter( request, response, filterChain )
        then:
            1 * response.sendError( 401, _ as String )
        and:
            0 * filterChain.doFilter( _ as HttpServletRequest, _ as HttpServletResponse )
    }

    def "it should hold api token in session if valid"() {
        given:
            request.getHeader( API_TOKEN_HEADER ) >> "TOKEN"
        and:
            tokenConfirmService.readApiToken( "TOKEN" ) >> new ApiTokenObject()
        when:
            tokenAccessDecisionFilter.doFilter( request, response, filterChain )
        then:
            1 * filterChain.doFilter( _ as HttpServletRequest, _ as HttpServletResponse )
    }

}
