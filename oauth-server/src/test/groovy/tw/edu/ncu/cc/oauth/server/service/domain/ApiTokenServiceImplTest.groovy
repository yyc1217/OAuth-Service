package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.apiToken.ApiTokenService

class ApiTokenServiceImplTest extends SpringSpecification {

    @Autowired
    ApiTokenService apiTokenService

    def "it can read client and use its api token with real api token 2"() {
        expect:
            apiTokenService.findUnexpiredByToken( "NOT EXIST" ) == null
    }

    @Transactional
    def "it can create api token"() {
        given:
            def apiToken = new_apiToken()
        when:
            def createdApiToken = apiTokenService.create( apiToken )
        and:
            def managedApiToken = apiTokenService.findUnexpiredByToken( createdApiToken.token )
        then:
            managedApiToken.client.name == apiToken.client.name
    }

    @Transactional
    def "it can revoke api token"() {
        given:
            def apiToken = apiTokenService.findUnexpiredByToken( a_apiToken().token )
        expect:
            apiToken != null
        when:
            apiTokenService.revoke( apiToken )
        then:
            apiTokenService.findUnexpiredByToken( a_apiToken().token ) == null
    }

    @Transactional
    def "it can refresh api token"() {
        given:
            def originApiToken = apiTokenService.findUnexpiredByToken( a_apiToken().token )
            def originEncryptedToken = originApiToken.encryptedToken
        when:
            def refreshedApiToken = apiTokenService.refreshToken( originApiToken )
        then:
            originEncryptedToken != refreshedApiToken.encryptedToken
        and:
            refreshedApiToken.token != null
    }

}
