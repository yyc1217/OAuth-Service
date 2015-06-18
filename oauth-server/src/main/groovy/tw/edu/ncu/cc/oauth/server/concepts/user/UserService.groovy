package tw.edu.ncu.cc.oauth.server.concepts.user

import javax.persistence.metamodel.Attribute

interface UserService {
    User create( User user )
    User update( User user )
    User findByName( String name )
    User findByName( String name, Attribute...attributes )
}