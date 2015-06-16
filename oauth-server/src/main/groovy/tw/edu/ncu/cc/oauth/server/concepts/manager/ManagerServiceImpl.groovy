package tw.edu.ncu.cc.oauth.server.concepts.manager

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.oauth.server.concepts.role.Role
import tw.edu.ncu.cc.oauth.server.concepts.role.RoleService
import tw.edu.ncu.cc.oauth.server.concepts.user.User
import tw.edu.ncu.cc.oauth.server.concepts.user.UserRepository
import tw.edu.ncu.cc.oauth.server.concepts.user.UserService

import javax.persistence.metamodel.Attribute

@Service
@CompileStatic
class ManagerServiceImpl implements ManagerService {

    @Autowired
    def UserService userService

    @Autowired
    def RoleService roleService

    @Autowired
    def UserRepository userRepository

    private final static String ROLE_ADMIN = "admin"

    @Override
    User findByName( String name, Attribute... attributes = [] ) {
        User user = userService.findByName( name, attributes )
        if( user == null ) {
            null
        } else {
            user.roles.find { it.name == ROLE_ADMIN } == null ? null : user
        }
    }

    @Override
    User create( User user ) {
        user.roles << adminRole()
        userRepository.save( user )
    }

    @Override
    List< User > findAllManagers( Pageable pageable ) {
        userRepository.findByRolePaged( adminRole(), pageable )
    }

    @Override
    User delete( User user ) {
        user.roles.remove( adminRole() )
        userRepository.save( user )
    }

    private Role adminRole() {
        roleService.findByName( ROLE_ADMIN )
    }

}
