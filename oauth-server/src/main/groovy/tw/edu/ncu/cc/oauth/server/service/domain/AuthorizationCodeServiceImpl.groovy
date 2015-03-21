package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tw.edu.ncu.cc.oauth.server.domain.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.User
import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret
import tw.edu.ncu.cc.oauth.server.service.security.SecretService

@Service
@Transactional
class AuthorizationCodeServiceImpl implements AuthorizationCodeService {

    @Autowired
    def SecretService secretService

    @Override
    AuthorizationCode create( AuthorizationCode authorizationCode ) {
        String code = secretService.generateToken()
        authorizationCode.code = secretService.encodeSecret( code )
        authorizationCode.save( failOnError: true, flush: true )
        authorizationCode.discard()
        authorizationCode.code = secretService.encodeSerialSecret( new SerialSecret( authorizationCode.id, code ) )
        return authorizationCode
    }

    @Override
    AuthorizationCode readUnexpiredByRealCode( String code, List includeField = [] ) {
        SerialSecret serialSecret = secretService.decodeSerialSecret( code )
        AuthorizationCode authorizationCode = readUnexpiredById( serialSecret.id as String, includeField )
        if( authorizationCode != null && secretService.matchesSecret( serialSecret.secret, authorizationCode.code ) ) {
            return authorizationCode
        } else {
            return null
        }
    }

    @Override
    AuthorizationCode readUnexpiredById( String codeId, List includeField = [] ) {
        AuthorizationCode.unexpired.include( includeField ).findWhere( id: codeId as long )
    }

    @Override
    List< AuthorizationCode > readAllUnexpiredByUser( User user, List includeField = [] ) {
        AuthorizationCode.unexpired.include( includeField ).findAllWhere( user: user )
    }

    @Override
    List< AuthorizationCode > readAllUnexpiredByClient( Client client, List includeField = [] ) {
        AuthorizationCode.unexpired.include( includeField ).findAllWhere( client: client )
    }

    @Override
    AuthorizationCode revoke( AuthorizationCode authorizationCode ) {
        authorizationCode.revoke()
        authorizationCode.save( failOnError: true, flush: true )
    }

    @Override
    boolean isCodeUnexpiredWithClientId( String code, String clientID ) {
        AuthorizationCode authorizationCode = readUnexpiredByRealCode( code )
        if( authorizationCode != null && authorizationCode.client.id == secretService.decodeHashId( clientID ) ) {
            return new Date().before( authorizationCode.dateExpired )
        } else {
            return false
        }
    }

}
