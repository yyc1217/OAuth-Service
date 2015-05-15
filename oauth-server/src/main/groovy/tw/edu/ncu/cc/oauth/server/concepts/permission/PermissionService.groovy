package tw.edu.ncu.cc.oauth.server.concepts.permission

interface PermissionService {
    Permission findById( int id )
    Permission findByName( String name )
    List< Permission > findAll()
}