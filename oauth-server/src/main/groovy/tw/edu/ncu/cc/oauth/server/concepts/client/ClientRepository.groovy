package tw.edu.ncu.cc.oauth.server.concepts.client

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

public interface ClientRepository extends JpaRepository< Client, Integer >, JpaSpecificationExecutor< Client > {


}
