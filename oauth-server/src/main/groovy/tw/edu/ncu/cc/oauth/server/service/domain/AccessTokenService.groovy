package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.*

interface AccessTokenService {

    AccessToken create( AccessToken accessToken );
    AccessToken createByAuthorizationCode( AccessToken accessToken, AuthorizationCode authorizationCode );
    AccessToken createByRefreshToken( AccessToken accessToken, RefreshToken refreshToken );

    AccessToken readAndUseUnexpiredByRealToken( String token );
    AccessToken readAndUseUnexpiredByRealToken( String token, List includeField );
    AccessToken readUnexpiredById  ( String tokenId );
    AccessToken readUnexpiredById  ( String tokenId, List includeField );

    List< AccessToken > readAllUnexpiredByUser( User user );
    List< AccessToken > readAllUnexpiredByUser( User user, List includeField );
    List< AccessToken > readAllUnexpiredByClient( Client client );
    List< AccessToken > readAllUnexpiredByClient( Client client, List includeField );

    AccessToken revoke( AccessToken accessToken );

}