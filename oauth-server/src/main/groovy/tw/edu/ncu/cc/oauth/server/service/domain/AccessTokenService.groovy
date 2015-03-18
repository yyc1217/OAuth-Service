package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.AccessToken


interface AccessTokenService {

    AccessToken create( AccessToken accessToken );
    AccessToken createByCode( AccessToken accessToken, String authorizationCode );

    AccessToken readUnexpiredByRealToken( String token );
    AccessToken readUnexpiredByRealToken( String token, List includeField );
    AccessToken readUnexpiredById  ( String tokenId );
    AccessToken readUnexpiredById  ( String tokenId, List includeField );

    List< AccessToken > readAllUnexpiredByUserName( String userName );
    List< AccessToken > readAllUnexpiredByUserName( String userName, List includeField );
    List< AccessToken > readAllUnexpiredByClientId( String clientId );
    List< AccessToken > readAllUnexpiredByClientId( String clientId, List includeField );

    AccessToken revoke( AccessToken accessToken );

}