package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.entity.AccessTokenEntity
import tw.edu.ncu.cc.oauth.server.service.AccessTokenService
import tw.edu.ncu.cc.oauth.server.service.ClientService
import tw.edu.ncu.cc.oauth.server.service.UserService


class AccessTokenServiceImplTest extends SpringSpecification {

    @Autowired
    private AccessTokenService accessTokenService

    @Autowired
    private ClientService clientService

    @Autowired
    private UserService userService

    @Transactional
    def "it can generate AccessTokenEntity"() {
        when:
            def token = accessTokenService.createAccessToken(
                    new AccessTokenEntity(
                            client: clientService.readClient( 1 ),
                            user  : userService.readUser( 1 ),
                            scope: "000"
                    )
            )
        then:
            accessTokenService.readAccessToken( token.getToken() ).getUser().getId() == 1
    }

    @Transactional
    def "it can delete AccessTokenEntity"() {
        given:
            def token = accessTokenService.createAccessToken(
                    new AccessTokenEntity(
                            client: clientService.readClient( 1 ),
                            user  : userService.readUser( 1 ),
                            scope: "000"
                    )
            )
        when:
            accessTokenService.deleteAccessToken(
                    accessTokenService.readAccessToken( token.getToken() )
            );
        then:
            accessTokenService.readAccessToken( token.getToken() ) == null
    }

    def "it can get AccessTokenEntity by id"() {
        expect:
            accessTokenService.readAccessToken( 1 ).getToken() == "TOKEN1"
    }

}
