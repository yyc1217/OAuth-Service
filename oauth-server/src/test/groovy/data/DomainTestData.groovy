package data

import org.springframework.beans.factory.annotation.Autowired
import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessTokenRepository
import tw.edu.ncu.cc.oauth.server.concepts.apiToken.ApiTokenRepository
import tw.edu.ncu.cc.oauth.server.concepts.authorizationCode.AuthorizationCodeRepository
import tw.edu.ncu.cc.oauth.server.concepts.client.ClientRepository
import tw.edu.ncu.cc.oauth.server.concepts.permission.PermissionRepository
import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshTokenRepository
import tw.edu.ncu.cc.oauth.server.concepts.user.UserRepository

trait DomainTestData {

    @Autowired
    def AccessTokenRepository AccessTokens

    @Autowired
    def ApiTokenRepository ApiTokens

    @Autowired
    def AuthorizationCodeRepository AuthorizationCodes

    @Autowired
    def RefreshTokenRepository RefreshTokens

    @Autowired
    def ClientRepository Clients

    @Autowired
    def PermissionRepository Permissions

    @Autowired
    def UserRepository Users

    public static Date laterTime() {
        return new Date( System.currentTimeMillis() + 1000000 )
    }

}