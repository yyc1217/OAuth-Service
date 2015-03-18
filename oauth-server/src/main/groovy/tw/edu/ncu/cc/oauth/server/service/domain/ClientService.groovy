package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.Client


interface ClientService {

    Client create( Client client )
    Client update( Client client )
    Client delete( Client client )
    Client readByID( String id )
    Client readByID( String id, List includeField )
    Client refreshSecret( Client client )
    boolean isIdSecretValid( String id, String secret )

}