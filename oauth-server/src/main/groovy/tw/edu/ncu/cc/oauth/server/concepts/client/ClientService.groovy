package tw.edu.ncu.cc.oauth.server.concepts.client

import javax.persistence.metamodel.Attribute

interface ClientService {

    Client create( Client client )
    Client update( Client client )
    Client delete( Client client )
    Client refreshSecret( Client client )
    Client findUndeletedBySerialId( String id )
    Client findUndeletedBySerialId( String id, Attribute...attributes )
    boolean isCredentialValid( String serialId, String secret )

}