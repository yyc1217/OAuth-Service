package tw.edu.ncu.cc.oauth.server.concepts.refreshToken

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

public interface RefreshTokenRepository extends JpaRepository< RefreshToken, Integer >, JpaSpecificationExecutor< RefreshToken > {


}
