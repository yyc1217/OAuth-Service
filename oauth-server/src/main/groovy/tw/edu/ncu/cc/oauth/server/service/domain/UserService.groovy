package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.User


interface UserService {
    User readByName( String name )
    User readByName( String name, List includeField )
    User createWithNameIfNotExist( String name )
    User createWithName( String name )
}