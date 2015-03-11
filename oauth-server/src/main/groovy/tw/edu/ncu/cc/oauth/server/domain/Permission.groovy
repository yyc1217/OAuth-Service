package tw.edu.ncu.cc.oauth.server.domain

import grails.persistence.Entity
import tw.edu.ncu.cc.oauth.server.domain.concern.Auditable

@Entity
class Permission implements Auditable {

    String name

    static hasMany = [
        accessTokens: AccessToken,
        authorizationCodes: AuthorizationCode,
        refreshTokens: RefreshToken
    ]

    static belongsTo = [
        AccessToken, AuthorizationCode, RefreshToken
    ]

}
