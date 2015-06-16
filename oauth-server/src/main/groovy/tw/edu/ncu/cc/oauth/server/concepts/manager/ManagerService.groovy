package tw.edu.ncu.cc.oauth.server.concepts.manager

import org.springframework.data.domain.Pageable
import tw.edu.ncu.cc.oauth.server.concepts.user.User

import javax.persistence.metamodel.Attribute

interface ManagerService {
    User create( User user )
    User delete( User user )
    User findByName( String name )
    User findByName( String name, Attribute...attributes )
    List< User > findAllManagers( Pageable pageable )
}