package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tw.edu.ncu.cc.oauth.server.domain.*
import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret
import tw.edu.ncu.cc.oauth.server.service.security.SecretService

@Service
@Transactional
class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    def SecretService secretService

    @Autowired
    def PermissionService permissionService

    @Autowired
    def RefreshTokenService refreshTokenService

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
    AccessToken createByAuthorizationCode( AccessToken accessToken, AuthorizationCode authorizationCode ) {
        authorizationCodeService.revoke( authorizationCode )
        accessToken.client = authorizationCode.client
        accessToken.scope = authorizationCode.scope.collect()
        accessToken.user = authorizationCode.user
        return create( accessToken )
    }

    @Override
    AccessToken createByRefreshToken( AccessToken accessToken, RefreshToken refreshToken ) {
        accessToken.client = refreshToken.client
        accessToken.scope = refreshToken.scope.collect()
        accessToken.user = refreshToken.user
        create( accessToken )
        revoke( refreshToken.accessToken )
        refreshToken.accessToken = accessToken
        refreshToken.save( failOnError: true )
        return accessToken
    }

    @Override
    AccessToken readAndUseUnexpiredByRealToken( String token, List includeField = [] ) {
        SerialSecret serialSecret = secretService.decodeSerialSecret( token )
        AccessToken accessToken = readUnexpiredById( serialSecret.id as String, includeField )
        if( accessToken != null && secretService.matchesSecret( serialSecret.secret, accessToken.token ) ) {
            accessToken.updateTimeStamp()
            accessToken.save( failOnError: true )
        } else {
            return null
        }
    }

    @Override
    AccessToken readUnexpiredById( String tokenId, List includeField = [] ) {
        AccessToken.unexpired.include( includeField ).findWhere( id : tokenId as long )
    }

    @Override
    List< AccessToken > readAllUnexpiredByUser( User user, List includeField = [] ) {
        AccessToken.unexpired.include( includeField ).findAllWhere( user : user )
    }

    @Override
    List< AccessToken > readAllUnexpiredByClient( Client client, List includeField = [] ) {
        AccessToken.unexpired.include( includeField ).findAllWhere( client : client )
    }

    @Override
    AccessToken revoke( AccessToken accessToken ) {
        accessToken.revoke()
        accessToken.save( failOnError: true, flush: true )
    }

}
