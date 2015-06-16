package tw.edu.ncu.cc.oauth.server.concepts.user

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.oauth.server.concepts.role.RoleRepository

import javax.persistence.metamodel.Attribute

import static org.springframework.data.jpa.domain.Specifications.where

@Service
@CompileStatic
class UserServiceImpl implements UserService {

    @Autowired
    def UserRepository userRepository

    @Autowired
    def RoleRepository roleRepository

    @Override
    User create( User user ) {
        userRepository.save( user )
    }

    @Override
    User update( User user ) {
        userRepository.save( user )
    }

    @Override
    User findByName( String serialId, Attribute... attributes = [] ) {
        userRepository.findOne(
                where( UserSpecifications.nameEquals( serialId ) )
                        .and( UserSpecifications.include( attributes ) )
        )
    }

}
