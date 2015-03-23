package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tw.edu.ncu.cc.oauth.server.domain.AccessToken
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.RefreshToken
import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret
import tw.edu.ncu.cc.oauth.server.service.security.SecretService

@Service
@Transactional
class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    def SecretService secretService

    @Override
    RefreshToken createByAccessToken( RefreshToken refreshToken, AccessToken accessToken ) {
        refreshToken.accessToken = accessToken
        refreshToken.client = accessToken.client
        refreshToken.scope = accessToken.scope.collect()
        refreshToken.user = accessToken.user
        return create( refreshToken )
    }

    private RefreshToken create( RefreshToken refreshToken ) {
        String token = secretService.generateToken()
        refreshToken.token = secretService.encodeSecret( token )
        refreshToken.save( failOnError: true, flush: true )
        refreshToken.discard()
        refreshToken.token = secretService.encodeSerialSecret( new SerialSecret( refreshToken.id, token ) )
        return refreshToken
    }

    @Override
    RefreshToken readUnexpiredByRealToken( String token, List includeField = [] ) {
        SerialSecret serialSecret = secretService.decodeSerialSecret( token )
        RefreshToken refreshToken = readUnexpiredById( serialSecret.id as String, includeField )
        if( refreshToken != null && secretService.matchesSecret( serialSecret.secret, refreshToken.token ) ) {
            return refreshToken
        } else {
            return null
        }
    }

    @Override
    RefreshToken readUnexpiredById( String tokenId, List includeField = [] ) {
        RefreshToken.unexpired.include( includeField ).findWhere( id: tokenId as long )
    }

    @Override
    List<RefreshToken> readAllUnexpiredByClient( Client client, List includeField = [] ) {
        RefreshToken.unexpired.include( includeField ).findAllWhere( client: client )
    }

    @Override
    RefreshToken revoke( RefreshToken refreshToken ) {
        refreshToken.revoke()
        refreshToken.save( failOnError: true )
    }

    @Override
    boolean isTokenUnexpiredWithClientId( String token, String clientID ) {
        RefreshToken refreshToken = readUnexpiredByRealToken( token )
        return refreshToken != null &&
               refreshToken.client.id == secretService.decodeHashId( clientID )
    }

}
