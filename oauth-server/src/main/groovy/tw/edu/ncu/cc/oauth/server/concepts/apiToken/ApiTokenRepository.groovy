package tw.edu.ncu.cc.oauth.server.concepts.apiToken

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

public interface ApiTokenRepository extends JpaRepository< ApiToken, Integer >, JpaSpecificationExecutor< ApiToken > {

}
