package tw.edu.ncu.cc.oauth.server.concepts.client

import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessToken
import tw.edu.ncu.cc.oauth.server.concepts.apiToken.ApiToken
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshToken
import tw.edu.ncu.cc.oauth.server.concepts.user.User
import tw.edu.ncu.cc.oauth.server.domain.BasicEntity_

import javax.persistence.metamodel.SetAttribute
import javax.persistence.metamodel.SingularAttribute
import javax.persistence.metamodel.StaticMetamodel

@StaticMetamodel( Client )
class Client_ extends BasicEntity_ {

    public static volatile SingularAttribute< Client, String > name
    public static volatile SingularAttribute< Client, String > encryptedSecret
    public static volatile SingularAttribute< Client, String > description
    public static volatile SingularAttribute< Client, String > url
    public static volatile SingularAttribute< Client, String > callback
    public static volatile SingularAttribute< Client, Boolean > deleted
    public static volatile SingularAttribute< Client, User > owner
    public static volatile SetAttribute< Client, ApiToken > apiTokens
    public static volatile SetAttribute< Client, RefreshToken > refreshTokens
    public static volatile SetAttribute< Client, AccessToken > accessTokens
    public static volatile SetAttribute< Client, AuthorizationCode > codes

}
