package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tw.edu.ncu.cc.oauth.server.domain.AccessToken
import tw.edu.ncu.cc.oauth.server.domain.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret
import tw.edu.ncu.cc.oauth.server.service.security.SecretService

@Service
@Transactional
class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    def SecretService secretService

    @Autowired
    def AuthorizationCodeService authorizationCodeService

    @Override
    AccessToken create( AccessToken accessToken ) {
        String token = secretService.generateToken()
        accessToken.token = secretService.encodeSecret( token )
        accessToken.save( failOnError: true, flush: true )
        accessToken.discard()
        accessToken.token = secretService.encodeSerialSecret( new SerialSecret( accessToken.id, token ) )
        return accessToken
    }

    @Override
    AccessToken createByCode( AccessToken accessToken, String authorizationCode ) {
        AuthorizationCode code = authorizationCodeService.readUnexpiredByRealCode( authorizationCode )
        if( code == null ) {
            return null
        } else {
            authorizationCodeService.revokeByID( code.id as String )
            accessToken.client = code.client
            accessToken.scope = code.scope
            accessToken.user = code.user
            code.discard()
            return create( accessToken )
        }
    }

    @Override
    AccessToken readUnexpiredByRealToken( String token, Map fetchOption = [:] ) {
        SerialSecret serialSecret = secretService.decodeSerialSecret( token )
        AccessToken accessToken = readUnexpiredById( serialSecret.id as String, fetchOption )
        if( accessToken != null && secretService.matchesSecret( serialSecret.secret, accessToken.token ) ) {
            return accessToken
        } else {
            return null
        }
    }

    @Override
    AccessToken readUnexpiredById( String tokenId, Map fetchOption = [:] ) {
        return AccessToken.where {
            id == "${tokenId}" as long && dateExpired > new Date()
        }.find(
            [ fetch: fetchOption ]
        )
    }

    @Override
    List< AccessToken > readAllUnexpiredByUserName( String userName, Map fetchOption = [:] ) {
        return AccessToken.where {
            user.name == "${userName}" && dateExpired > new Date()
        }.list(
            [ fetch: fetchOption ]
        )
    }

    @Override
    List< AccessToken > readAllUnexpiredByClientId( String clientId, Map fetchOption = [:] ) {
        return AccessToken.where {
            client.id == "${clientId}" as long && dateExpired > new Date()
        }.list(
            [ fetch: fetchOption ]
        )
    }

    @Override
    AccessToken revoke( AccessToken accessToken ) {
        accessToken.revoke()
        accessToken.save( failOnError: true )
    }

}
