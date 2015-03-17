package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.AuthorizationCode


interface AuthorizationCodeService {

    AuthorizationCode create( AuthorizationCode authorizationCode );

    AuthorizationCode readUnexpiredByRealCode( String code );
    AuthorizationCode readUnexpiredByRealCode( String code, Map fetchOption );
    AuthorizationCode readUnexpiredById( String codeId );
    AuthorizationCode readUnexpiredById( String codeId, Map fetchOption );

    List< AuthorizationCode > readAllUnexpiredByUserName( String userName );
    List< AuthorizationCode > readAllUnexpiredByUserName( String userName, Map fetchOption );
    List< AuthorizationCode > readAllUnexpiredByClientId( String clientId );
    List< AuthorizationCode > readAllUnexpiredByClientId( String clientId, Map fetchOption );

    AuthorizationCode revokeByID( String codeId );

    boolean isCodeUnexpiredWithClientId( String authCode, String clientID );
}