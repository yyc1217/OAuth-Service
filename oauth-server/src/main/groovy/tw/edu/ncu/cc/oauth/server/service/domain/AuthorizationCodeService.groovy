package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.User


interface AuthorizationCodeService {

    AuthorizationCode create( AuthorizationCode authorizationCode );

    AuthorizationCode readUnexpiredByRealCode( String code );
    AuthorizationCode readUnexpiredByRealCode( String code, List includeField );
    AuthorizationCode readUnexpiredById( String codeId );
    AuthorizationCode readUnexpiredById( String codeId, List includeField );

    List< AuthorizationCode > readAllUnexpiredByUser( User user );
    List< AuthorizationCode > readAllUnexpiredByUser( User user, List includeField );
    List< AuthorizationCode > readAllUnexpiredByClient( Client client );
    List< AuthorizationCode > readAllUnexpiredByClient( Client client, List includeField );

    AuthorizationCode revoke( AuthorizationCode authorizationCode );

    boolean isCodeUnexpiredWithClientId( String authCode, String clientID );
}