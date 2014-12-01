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
            def token = accessTokenService.generateAccessToken(
                    new AccessTokenEntity(
                            client: clientService.getClient( 1 ),
                            user  : userService.getUser( 1 ),
                            scope: "000"
                    )
            )
        then:
            accessTokenService.getAccessToken( token.getToken() ).getUser().getId() == 1
    }

    @Transactional
    def "it can delete AccessTokenEntity"() {
        given:
            def token = accessTokenService.generateAccessToken(
                    new AccessTokenEntity(
                            client: clientService.getClient( 1 ),
                            user  : userService.getUser( 1 ),
                            scope: "000"
                    )
            )
        when:
            accessTokenService.deleteAccessToken(
                    accessTokenService.getAccessToken( token.getToken() )
            );
        then:
            accessTokenService.getAccessToken( token.getToken() ) == null
    }

    def "it can get AccessTokenEntity by id"() {
        expect:
            accessTokenService.getAccessToken( 1 ).getToken() == "TOKEN1"
    }

}
