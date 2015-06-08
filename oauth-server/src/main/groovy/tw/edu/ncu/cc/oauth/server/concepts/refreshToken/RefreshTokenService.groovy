package tw.edu.ncu.cc.oauth.server.concepts.refreshToken

import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessToken
import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.concepts.user.User

import javax.persistence.metamodel.Attribute

interface RefreshTokenService {

    RefreshToken createByAccessToken( RefreshToken refreshToken, AccessToken accessToken )

    RefreshToken readUnexpiredByToken( String token )
    RefreshToken readUnexpiredByToken( String token, Attribute...attributes )
    RefreshToken readUnexpiredById  ( String tokenId )
    RefreshToken readUnexpiredById  ( String tokenId, Attribute...attributes )

    List< RefreshToken > readAllUnexpiredByUser( User user )
    List< RefreshToken > readAllUnexpiredByUser( User user, Attribute...attributes )
    List< RefreshToken > readAllUnexpiredByClient( Client client )
    List< RefreshToken > readAllUnexpiredByClient( Client client, Attribute...attributes )

    RefreshToken revoke( RefreshToken refreshToken )

    boolean isUnexpiredTokenMatchesClientId( String token, String clientID )
}