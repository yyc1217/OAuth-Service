package tw.edu.ncu.cc.oauth.server.service.domain

import tw.edu.ncu.cc.oauth.server.domain.Client


interface ClientService {

    Client create( Client client )
    Client update( Client client )
    Client delete( Client client )
    Client readBySerialId( String id )
    Client readBySerialId( String id, List includeField )
    Client refreshSecret( Client client )
    boolean isCredentialValid( String serialId, String secret )

}