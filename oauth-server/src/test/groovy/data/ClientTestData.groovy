package data

import org.springframework.beans.factory.annotation.Autowired
import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.concepts.security.SecretService

trait ClientTestData extends DomainTestData {

    @Autowired
    private SecretService secretService

    def String serialId( long id ) {
        secretService.encodeHashId( id )
    }

    Client new_client(){
        new Client(
                name: "HelloWorld",
                description: "description",
                callback: "abc://123",
                owner: getUsers().findOne( 1 ),
                url: "http://example.com",
                deleted: false
        )
    }

    Client a_client() {
        new Client(
                id: 3,
                name: "APP3",
                encryptedSecret: "SECRET",
                owner: getUsers().findOne( 3 ),
                callback: "http://example.com",
                url: "http://example.com",
                description: "3333",
                deleted: false
        )
    }

    Client get_client( int id ) {
        def client = getClients().findOne( id )
        client.getOwner()
        client
    }

}