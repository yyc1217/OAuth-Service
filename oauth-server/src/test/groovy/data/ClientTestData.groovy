package data

import org.springframework.beans.factory.annotation.Autowired
import tw.edu.ncu.cc.oauth.server.domain.Client
import tw.edu.ncu.cc.oauth.server.domain.User
import tw.edu.ncu.cc.oauth.server.service.security.SecretService

trait ClientTestData extends DomainTestData {

    @Autowired
    private SecretService secretService

    def String serialId( long id ) {
        secretService.encodeHashId( id )
    }

    static Client new_client(){
        new Client(
                name: "HelloWorld",
                description: "description",
                callback: "abc://123",
                owner: User.get( 1 ),
                url: "http://example.com"
        )
    }

    static Client a_client() {
        new Client(
                id: 3,
                name: "APP3",
                secret: "SECRET",
                owner: User.get( 3 ),
                callback: "http://example.com",
                url: "http://example.com",
                description: "3333"
        )
    }

    static Client get_client( long id ) {
        Client.include( [ 'owner' ] ).get( id )
    }

}