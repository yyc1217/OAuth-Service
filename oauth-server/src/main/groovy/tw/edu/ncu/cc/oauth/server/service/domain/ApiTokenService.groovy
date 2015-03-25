package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.Client


interface ApiTokenService {

    Client readAndUseByRealToken( String token );
}