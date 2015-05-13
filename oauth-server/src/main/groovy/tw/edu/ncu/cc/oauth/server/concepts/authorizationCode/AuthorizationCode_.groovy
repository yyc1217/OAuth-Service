package tw.edu.ncu.cc.oauth.server.concepts.authorizationCode

import tw.edu.ncu.cc.oauth.server.concepts.permission.Permission

import javax.persistence.metamodel.SetAttribute
import javax.persistence.metamodel.SingularAttribute
import javax.persistence.metamodel.StaticMetamodel

@StaticMetamodel( AuthorizationCode )
class AuthorizationCode_ {

    public static volatile SingularAttribute< AuthorizationCode, String > encryptedCode
    public static volatile SetAttribute< AuthorizationCode, Permission > scope

}
