package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.AccessToken
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.RefreshToken

interface RefreshTokenService {

    RefreshToken createByAccessToken( RefreshToken refreshToken, AccessToken accessToken );

    RefreshToken readUnexpiredByRealToken( String token );
    RefreshToken readUnexpiredByRealToken( String token, List includeField );
    RefreshToken readUnexpiredById  ( String tokenId );
    RefreshToken readUnexpiredById  ( String tokenId, List includeField );

    List< RefreshToken > readAllUnexpiredByClient( Client client );
    List< RefreshToken > readAllUnexpiredByClient( Client client, List includeField );

    RefreshToken revoke( RefreshToken refreshToken );

    boolean isTokenUnexpiredWithClientId( String refreshToken, String clientID );
}