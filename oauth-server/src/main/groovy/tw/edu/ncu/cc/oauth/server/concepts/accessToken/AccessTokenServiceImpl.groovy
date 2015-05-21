package tw.edu.ncu.cc.oauth.server.concepts.accessToken

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCodeService
import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshToken
import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshTokenRepository
import tw.edu.ncu.cc.oauth.server.concepts.security.SecretService
import tw.edu.ncu.cc.oauth.server.concepts.user.User
import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret

import javax.persistence.metamodel.Attribute

import static org.springframework.data.jpa.domain.Specifications.where

@Service
@CompileStatic
class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    def AccessTokenRepository accessTokenRepository

    @Autowired
    def SecretService secretService

    @Autowired
    def RefreshTokenRepository refreshTokenRepository

    @Autowired
    def AuthorizationCodeService authorizationCodeService

    @Override
    @Transactional
    AccessToken create( AccessToken accessToken ) {
        String token = secretService.generateToken()
        accessToken.encryptedToken = secretService.encrypt( token )
        accessTokenRepository.save( accessToken )
        accessToken.token = secretService.encodeSerialSecret( new SerialSecret( accessToken.id, token ) )
        accessToken
    }

    @Override
    @Transactional
    AccessToken createByAuthorizationCode( AccessToken accessToken, AuthorizationCode authorizationCode ) {
        authorizationCodeService.revoke( authorizationCode )
        accessToken.client = authorizationCode.client
        accessToken.scope = authorizationCode.scope.collect().toSet()
        accessToken.user = authorizationCode.user
        return create( accessToken )
    }

    @Override
    @Transactional
    AccessToken createByRefreshToken( AccessToken accessToken, RefreshToken refreshToken ) {
        accessToken.client = refreshToken.client
        accessToken.scope = refreshToken.scope.collect().toSet()
        accessToken.user = refreshToken.user
        create( accessToken )
        revoke( refreshToken.accessToken )
        refreshToken.accessToken = accessToken
        refreshTokenRepository.save( refreshToken )
        accessToken
    }

    @Override
    @Transactional
    AccessToken revoke( AccessToken accessToken ) {
        accessToken.revoke()
        accessTokenRepository.save( accessToken )
    }

    @Override
    AccessToken findUnexpiredByToken( String token, Attribute...attributes = [] ) {
        SerialSecret serialSecret = secretService.decodeSerialSecret( token )
        AccessToken accessToken = findUnexpiredById( serialSecret.id as String, attributes )
        if( accessToken != null && secretService.matches( serialSecret.secret, accessToken.encryptedToken ) ) {
            accessToken.refreshTimeStamp()
            accessTokenRepository.save( accessToken )
        } else {
            return null
        }
    }

    @Override
    AccessToken findUnexpiredById( String tokenId, Attribute...attributes = [] ) {
        accessTokenRepository.findOne(
                where( AccessTokenSpecifications.unexpired() )
                        .and( AccessTokenSpecifications.idEquals( tokenId as Integer ) )
                        .and( AccessTokenSpecifications.include( attributes ) )
        )
    }

    @Override
    List< AccessToken > findAllUnexpiredByUser( User user, Attribute...attributes= [] ) {
        accessTokenRepository.findAll(
                where( AccessTokenSpecifications.unexpired() )
                        .and( AccessTokenSpecifications.userEquals( user ) )
                        .and( AccessTokenSpecifications.include( attributes ) )
        )
    }

    @Override
    List< AccessToken > findAllUnexpiredByClient( Client client, Attribute...attributes = [] ) {
        accessTokenRepository.findAll(
                where( AccessTokenSpecifications.unexpired() )
                        .and( AccessTokenSpecifications.clientEquals( client ) )
                        .and( AccessTokenSpecifications.include( attributes ) )
        )
    }

}
