package tw.edu.ncu.cc.oauth.server.domain

import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.concepts.user.User

import javax.persistence.metamodel.SingularAttribute
import javax.persistence.metamodel.StaticMetamodel


@StaticMetamodel( TokenEntity )
class TokenEntity_ {
    public static volatile SingularAttribute< TokenEntity, User > user
    public static volatile SingularAttribute< TokenEntity, Client > client
    public static volatile SingularAttribute< TokenEntity, Date > dateExpired
}
