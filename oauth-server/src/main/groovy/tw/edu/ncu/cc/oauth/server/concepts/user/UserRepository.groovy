package tw.edu.ncu.cc.oauth.server.concepts.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

public interface UserRepository extends JpaRepository< User, Integer >, JpaSpecificationExecutor< User > {

    User findByName( String name )
}
