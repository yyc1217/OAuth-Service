package data

import org.springframework.transaction.annotation.Transactional
import tw.edu.ncu.cc.oauth.server.concepts.accessToken.AccessToken

trait AccessTokenTestData extends DomainTestData {

    AccessToken new_accessToken(){
        new AccessToken(
                client: getClients().findOne( 1 ),
                user: getUsers().findOne( 1 ),
                scope: [ getPermissions().findOne( 1 ) ],
                dateExpired: laterTime()
        )
    }

    AccessToken a_accessToken() {
        new AccessToken(
                id: 3,
                client: getClients().findOne( 3 ),
                user: getUsers().findOne( 3 ),
                scope: [ getPermissions().findOne( 1 ), getPermissions().findOne( 2 ) ],
                token: "Mzo6OlRPS0VO"
        )
    }

    @Transactional
    AccessToken get_accessToken( int id ) {
        def accesstoken = getAccessTokens().findOne( id )
        accesstoken.getScope()
        accesstoken.getClient()
        accesstoken.getUser()
        accesstoken
    }

}