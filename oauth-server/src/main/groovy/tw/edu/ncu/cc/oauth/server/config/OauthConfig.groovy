package tw.edu.ncu.cc.oauth.server.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
public class OauthConfig {

    @Value( '${custom.oauth.authCode.expire-seconds}' )
    def long authCodeExpireSeconds=600;

    @Value( '${custom.oauth.accessToken.expire-seconds}' )
    def long accessTokenExpireSeconds=600;

}
