package tw.edu.ncu.cc.oauth.server.concepts.accessToken

import tw.edu.ncu.cc.oauth.server.concepts.permission.Permission

import javax.persistence.metamodel.SetAttribute
import javax.persistence.metamodel.SingularAttribute
import javax.persistence.metamodel.StaticMetamodel

@StaticMetamodel( AccessToken )
class AccessToken_ {

    public static volatile SingularAttribute< AccessToken, String > encryptedToken
    public static volatile SetAttribute< AccessToken, Permission > scope

}
