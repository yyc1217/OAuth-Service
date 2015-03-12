package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.AccessToken


interface AccessTokenService {

    AccessToken create( AccessToken accessToken );
    AccessToken createByCode( AccessToken accessToken, String authorizationCode );

    AccessToken readUnexpiredByRealToken( String token );
    AccessToken readUnexpiredByRealToken( String token, Map fetchOption );
    AccessToken readUnexpiredById  ( String tokenId );
    AccessToken readUnexpiredById  ( String tokenId, Map fetchOption );

    List< AccessToken > readAllUnexpiredByUserName( String userName );
    List< AccessToken > readAllUnexpiredByUserName( String userName, Map fetchOption );
    List< AccessToken > readAllUnexpiredByClientId( String clientId );
    List< AccessToken > readAllUnexpiredByClientId( String clientId, Map fetchOption );

    AccessToken revoke( AccessToken accessToken );

}