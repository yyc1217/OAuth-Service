package tw.edu.ncu.cc.oauth.server.concepts.accessToken

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

public interface AccessTokenRepository extends JpaRepository< AccessToken, Integer >, JpaSpecificationExecutor< AccessToken > {


}
