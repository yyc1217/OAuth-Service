package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.AuthorizationCode


interface AuthorizationCodeService {

    AuthorizationCode create( AuthorizationCode authorizationCode );

    AuthorizationCode readUnexpiredByRealCode( String code );
    AuthorizationCode readUnexpiredByRealCode( String code, List includeField );
    AuthorizationCode readUnexpiredById( String codeId );
    AuthorizationCode readUnexpiredById( String codeId, List includeField );

    List< AuthorizationCode > readAllUnexpiredByUserName( String userName );
    List< AuthorizationCode > readAllUnexpiredByUserName( String userName, List includeField );
    List< AuthorizationCode > readAllUnexpiredByClientId( String clientId );
    List< AuthorizationCode > readAllUnexpiredByClientId( String clientId, List includeField );

    AuthorizationCode revokeByID( String codeId );

    boolean isCodeUnexpiredWithClientId( String authCode, String clientID );
}