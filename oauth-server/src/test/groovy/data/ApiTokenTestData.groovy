package data

import tw.edu.ncu.cc.oauth.server.domain.ApiToken
import tw.edu.ncu.cc.oauth.server.domain.Client

trait ApiTokenTestData extends DomainTestData {

    static ApiToken new_apiToken(){
        new ApiToken(
                client: Client.get( 1 ),
                dateExpired: laterTime()
        )
    }

    static ApiToken a_apiToken() {
        new ApiToken(
                id: 3,
                client: Client.get( 3 ),
                token: "Mzo6OlRPS0VO"
        )
    }

}