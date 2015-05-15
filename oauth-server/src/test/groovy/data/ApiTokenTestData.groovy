package data

import tw.edu.ncu.cc.oauth.server.concepts.apiToken.ApiToken

trait ApiTokenTestData extends DomainTestData {

    ApiToken new_apiToken(){
        new ApiToken(
                client: getClients().findOne( 1 ),
                dateExpired: laterTime()
        )
    }

    ApiToken a_apiToken() {
        new ApiToken(
                id: 3,
                client: getClients().findOne( 3 ),
                token: "Mzo6OlRPS0VO"
        )
    }

}