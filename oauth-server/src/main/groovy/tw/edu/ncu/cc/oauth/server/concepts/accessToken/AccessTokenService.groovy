package tw.edu.ncu.cc.oauth.server.concepts.accessToken

import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshToken
import tw.edu.ncu.cc.oauth.server.concepts.user.User

import javax.persistence.metamodel.Attribute

interface AccessTokenService {

    AccessToken create( AccessToken accessToken );
    AccessToken createByAuthorizationCode( AccessToken accessToken, AuthorizationCode authorizationCode );
    AccessToken createByRefreshToken( AccessToken accessToken, RefreshToken refreshToken );

    AccessToken findUnexpiredByToken( String token );
    AccessToken findUnexpiredByToken( String token, Attribute...attributes );
    AccessToken findUnexpiredById( String tokenId );
    AccessToken findUnexpiredById( String tokenId, Attribute...attributes );

    List< AccessToken > findAllUnexpiredByUser( User user );
    List< AccessToken > findAllUnexpiredByUser( User user, Attribute...attributes );
    List< AccessToken > findAllUnexpiredByClient( Client client );
    List< AccessToken > findAllUnexpiredByClient( Client client, Attribute...attributes );

    AccessToken revoke( AccessToken accessToken );

}