package tw.edu.ncu.cc.oauth.server.concepts.user

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import tw.edu.ncu.cc.oauth.server.concepts.role.Role

public interface UserRepository extends JpaRepository< User, Integer >, JpaSpecificationExecutor< User > {

    User findByName( String name )

    @Query( "from User u where ?1 member of u.roles" )
    List< User > findByRolePaged( Role role, Pageable pageable )

}
