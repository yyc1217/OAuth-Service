package tw.edu.ncu.cc.oauth.server.service.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tw.edu.ncu.cc.oauth.server.domain.AuthorizationCode
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
    AuthorizationCode readUnexpiredByRealCode( String code, Map fetchOption = [:] ) {
        SerialSecret serialSecret = secretService.decodeSerialSecret( code )
        AuthorizationCode authorizationCode = readUnexpiredById( serialSecret.id as String )
        if( authorizationCode != null && secretService.matchesSecret( serialSecret.secret, authorizationCode.code ) ) {
            return authorizationCode
        } else {
            return null
        }
    }

    @Override
    AuthorizationCode readUnexpiredById( String codeId, Map fetchOption = [:] ) {
        return AuthorizationCode.where{
            id == "${codeId}" as long && dateExpired > new Date()
        }.find(
            [ fetch: fetchOption ]
        )
    }

    @Override
    List< AuthorizationCode > readAllUnexpiredByUserName( String userName, Map fetchOption = [:] ) {
        return AuthorizationCode.where{
            user.name == "${userName}" && dateExpired > new Date()
        }.list(
            [ fetch: fetchOption ]
        )
    }

    @Override
    List< AuthorizationCode > readAllUnexpiredByClientId( String clientId, Map fetchOption = [:] ) {
        return AuthorizationCode.where{
            client.id == "${clientId}" as long && dateExpired > new Date()
        }.list(
            [ fetch: fetchOption ]
        )
    }

    @Override
    AuthorizationCode revokeByID( String codeId ) {
        AuthorizationCode authorizationCode = readUnexpiredById( codeId )
        if( authorizationCode == null ) {
            return null
        }  else {
            authorizationCode.revoke()
            authorizationCode.save( failOnError: true, flush: true )
        }
    }

    @Override
    boolean isCodeUnexpiredWithClientId( String code, String clientID ) {
        AuthorizationCode authorizationCode = readUnexpiredByRealCode( code )
        if( authorizationCode != null && authorizationCode.client.id.toString().equals( clientID ) ) {
            return new Date().before( authorizationCode.dateExpired )
        } else {
            return false
        }
    }

}
