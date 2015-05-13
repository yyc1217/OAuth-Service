package tw.edu.ncu.cc.oauth.server.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.concepts.refreshToken.RefreshTokenRepository


class RefreshTokenRepositoryTest extends SpringSpecification {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository

    @Transactional
    def "it can map to exist data"() {
        when:
            def refreshToken = refreshTokenRepository.findOne( 1 )
        then:
            refreshToken.encryptedToken == "TOKEN1"
            refreshToken.accessToken.id == 1
            refreshToken.client.name == "APP1"
            refreshToken.user.name == "ADMIN1"
            refreshToken.scope.size() == 2
    }

}
