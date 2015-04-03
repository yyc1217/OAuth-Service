package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tw.edu.ncu.cc.oauth.server.domain.ApiToken
import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret
import tw.edu.ncu.cc.oauth.server.service.security.SecretService

@Service
@Transactional
class ApiTokenServiceImpl implements ApiTokenService {

    @Autowired
    def SecretService secretService

    @Autowired
    def ClientService clientService

    @Override
    ApiToken create( ApiToken apiToken ) {
        String token = secretService.generateToken()
        apiToken.token = secretService.encodeSecret( token )
        apiToken.save( failOnError: true, flush: true )
        apiToken.discard()
        apiToken.token = secretService.encodeSerialSecret( new SerialSecret( apiToken.id, token ) )
        return apiToken
    }

    @Override
    ApiToken readAndUseByRealToken( String token, List includeField = [ 'client' ] ) {
        SerialSecret serialSecret = secretService.decodeSerialSecret( token )
        ApiToken apiToken = ApiToken.include( includeField ).unexpired.findWhere( id: serialSecret.id )
        if( apiToken != null && secretService.matchesSecret( serialSecret.secret, apiToken.token ) ) {
            apiToken.client.apiUseTimes += 1
            apiToken.updateTimeStamp()
            apiToken.save( failOnError: true )
        } else {
            return null
        }
    }

}
