package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret
import tw.edu.ncu.cc.oauth.server.service.security.SecretService

@Service
@Transactional
class ApiTokenServiceImpl implements ApiTokenService {

    @Autowired
    def SecretService secretService

    @Override
    Client readAndUseByRealToken( String token ) {
        SerialSecret serialSecret = secretService.decodeSerialSecret( token )
        Client client = Client.findWhere( id: serialSecret.id )
        if( client != null && secretService.matchesSecret( serialSecret.secret, client.apiToken ) ) {
            client.apiUseTimes += 1
            client.save( failOnError: true )
        } else {
            return null
        }
    }

}
