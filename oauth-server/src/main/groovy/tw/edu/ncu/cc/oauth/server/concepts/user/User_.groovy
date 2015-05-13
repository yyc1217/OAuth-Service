package tw.edu.ncu.cc.oauth.server.concepts.user

import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessToken
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCode
import tw.edu.ncu.cc.oauth.server.concepts.client.Client

import javax.persistence.metamodel.SetAttribute
import javax.persistence.metamodel.SingularAttribute
import javax.persistence.metamodel.StaticMetamodel

@StaticMetamodel( User )
class User_ {
    public static volatile SingularAttribute< User, String > name
    public static volatile SetAttribute< User, Client > clients
    public static volatile SetAttribute< User, AuthorizationCode > codes
    public static volatile SetAttribute< User, AccessToken > accessTokens
}
