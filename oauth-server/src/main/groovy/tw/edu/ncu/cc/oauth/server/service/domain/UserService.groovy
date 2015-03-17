package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.User


interface UserService {
    User readByName( String name )
    User readByName( String name, Map fetchOption )
    User createWithNameIfNotExist( String name )
    User createWithName( String name )
}