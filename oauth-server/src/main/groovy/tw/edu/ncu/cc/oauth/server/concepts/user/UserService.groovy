package tw.edu.ncu.cc.oauth.server.concepts.user

import javax.persistence.metamodel.Attribute

interface UserService {
    User findByName( String name )
    User findByName( String name, Attribute...attributes )
    User createByNameIfNotExist( String name )
    User createByName( String name )
}