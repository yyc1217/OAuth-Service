package tw.edu.ncu.cc.oauth.server.concepts.authorizationCode

import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.concepts.user.User

import javax.persistence.metamodel.Attribute


interface AuthorizationCodeService {

    AuthorizationCode create( AuthorizationCode authorizationCode );
    AuthorizationCode revoke( AuthorizationCode authorizationCode );

    AuthorizationCode readUnexpiredByCode( String code );
    AuthorizationCode readUnexpiredByCode( String code, Attribute...attributes );
    AuthorizationCode readUnexpiredById( String codeId );
    AuthorizationCode readUnexpiredById( String codeId, Attribute...attributes );

    List< AuthorizationCode > readAllUnexpiredByUser( User user );
    List< AuthorizationCode > readAllUnexpiredByUser( User user, Attribute...attributes );
    List< AuthorizationCode > readAllUnexpiredByClient( Client client );
    List< AuthorizationCode > readAllUnexpiredByClient( Client client, Attribute...attributes );

    boolean isUnexpiredCodeMatchesClientId( String authCode, String clientID );
}