package tw.edu.ncu.cc.oauth.server.concepts.apiToken

import tw.edu.ncu.cc.oauth.server.concepts.client.Client

import javax.persistence.metamodel.SingularAttribute
import javax.persistence.metamodel.StaticMetamodel

@StaticMetamodel( ApiToken )
class ApiToken_ {

    public static volatile SingularAttribute< ApiToken, Date > dateExpired
    public static volatile SingularAttribute< ApiToken, String > encryptedToken
    public static volatile SingularAttribute< ApiToken, Client > client

}
