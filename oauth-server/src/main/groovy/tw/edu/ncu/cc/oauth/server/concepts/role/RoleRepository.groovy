package tw.edu.ncu.cc.oauth.server.concepts.role

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface RoleRepository extends JpaRepository< Role, Integer >, JpaSpecificationExecutor< Role > {

    Role findByName( String name )

}