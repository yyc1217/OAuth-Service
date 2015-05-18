package tw.edu.ncu.cc.oauth.resource.test

import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.web.servlet.request.RequestPostProcessor
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.resource.core.ApiCredentialHolder

import static tw.edu.ncu.cc.oauth.resource.config.RequestConfig.ACCESS_TOKEN_HEADER
import static tw.edu.ncu.cc.oauth.resource.config.RequestConfig.ACCESS_TOKEN_PREFIX

class OauthTokenRequestPostProcessor implements RequestPostProcessor {

    private String id
    private String user
    private String clientId
    private String accessToken
    private String[] scope

    OauthTokenRequestPostProcessor( String accessToken ) {
        this.accessToken = accessToken
    }

    OauthTokenRequestPostProcessor id( String id ) {
        this.id = id
        this
    }

    OauthTokenRequestPostProcessor user( String user ) {
        this.user = user
        this
    }

    OauthTokenRequestPostProcessor clientId( String clientId ) {
        this.clientId = clientId
        this
    }

    OauthTokenRequestPostProcessor scope( String[] scope ) {
        this.scope = scope
        this
    }

    @Override
    MockHttpServletRequest postProcessRequest( MockHttpServletRequest request ) {
        request.addHeader( ACCESS_TOKEN_HEADER, ACCESS_TOKEN_PREFIX + " " + accessToken )
        ApiCredentialHolder.addAccessToken( accessToken, new AccessTokenObject(
                id: id,
                user: user,
                scope: scope,
                client_id: clientId,
                last_updated: new Date()
        ) )
        request
    }

}
