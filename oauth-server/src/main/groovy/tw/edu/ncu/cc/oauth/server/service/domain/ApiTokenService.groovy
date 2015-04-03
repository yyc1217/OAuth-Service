package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.ApiToken

interface ApiTokenService {

    ApiToken create( ApiToken apiToken )
    ApiToken readAndUseByRealToken( String token )
    ApiToken readAndUseByRealToken( String token, List includeField )
}