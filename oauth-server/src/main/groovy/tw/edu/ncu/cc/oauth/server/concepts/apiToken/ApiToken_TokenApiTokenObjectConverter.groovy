package tw.edu.ncu.cc.oauth.server.concepts.apiToken

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.data.v1.management.token.TokenApiTokenObject
import tw.edu.ncu.cc.oauth.server.concepts.security.SecretService
import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret

@Component
class ApiToken_TokenApiTokenObjectConverter implements Converter< ApiToken, TokenApiTokenObject >{

    @Autowired
    def SecretService secretService

    @Override
    TokenApiTokenObject convert( ApiToken source ) {
        TokenApiTokenObject apiTokenObject = new TokenApiTokenObject()
        apiTokenObject.id = source.id
        apiTokenObject.token = calculateUserSideToken( source )
        apiTokenObject.client_id = secretService.encodeHashId( source.client.id )
        apiTokenObject
    }

    private String calculateUserSideToken( ApiToken apiToken ) {
        if( apiToken.token != null ) {
            apiToken.token
        }  else {
            secretService.encodeSerialSecret( new SerialSecret( apiToken.id, token( apiToken ) ) )
        }
    }

    private String token( ApiToken apiToken ) {
        secretService.decrypt( apiToken.encryptedToken )
    }

}
