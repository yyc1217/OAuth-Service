package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.Permission


interface PermissionService {
    Permission readById( long id )
    Permission readByName( String name )
    List< Permission > readAll()
}