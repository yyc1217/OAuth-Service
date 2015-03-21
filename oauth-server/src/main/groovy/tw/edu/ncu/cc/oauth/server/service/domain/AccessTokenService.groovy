package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.AccessToken
import tw.edu.ncu.cc.oauth.server.domain.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.User


interface AccessTokenService {

    AccessToken create( AccessToken accessToken );
    AccessToken createByAuthorizationCode( AccessToken accessToken, AuthorizationCode authorizationCode );

    AccessToken readUnexpiredByRealToken( String token );
    AccessToken readUnexpiredByRealToken( String token, List includeField );
    AccessToken readUnexpiredById  ( String tokenId );
    AccessToken readUnexpiredById  ( String tokenId, List includeField );

    List< AccessToken > readAllUnexpiredByUser( User user );
    List< AccessToken > readAllUnexpiredByUser( User user, List includeField );
    List< AccessToken > readAllUnexpiredByClient( Client client );
    List< AccessToken > readAllUnexpiredByClient( Client client, List includeField );

    AccessToken revoke( AccessToken accessToken );

}