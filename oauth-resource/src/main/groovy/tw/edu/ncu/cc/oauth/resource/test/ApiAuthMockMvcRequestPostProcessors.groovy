package tw.edu.ncu.cc.oauth.resource.test


class ApiAuthMockMvcRequestPostProcessors {

    static ApiTokenRequestPostProcessor apiToken( String apiToken = UUID.randomUUID().toString() ) {
        new ApiTokenRequestPostProcessor( apiToken )
    }

    static OauthTokenRequestPostProcessor accessToken( String oauthToken = UUID.randomUUID().toString() ) {
        new OauthTokenRequestPostProcessor( oauthToken )
    }

}
