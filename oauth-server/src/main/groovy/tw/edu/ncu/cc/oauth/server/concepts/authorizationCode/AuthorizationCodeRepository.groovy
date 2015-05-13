package tw.edu.ncu.cc.oauth.server.concepts.authorizationCode

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

public interface AuthorizationCodeRepository extends JpaRepository< AuthorizationCode, Integer >, JpaSpecificationExecutor< AuthorizationCode > {


}
