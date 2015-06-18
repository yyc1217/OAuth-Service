package tw.edu.ncu.cc.oauth.server.concepts.authorizationCode

import tw.edu.ncu.cc.oauth.server.concepts.client.Client
import tw.edu.ncu.cc.oauth.server.concepts.user.User

import javax.persistence.metamodel.Attribute


interface AuthorizationCodeService {

    AuthorizationCode create( AuthorizationCode authorizationCode );
    AuthorizationCode revoke( AuthorizationCode authorizationCode );

    AuthorizationCode findUnexpiredByCode( String code );
    AuthorizationCode findUnexpiredByCode( String code, Attribute...attributes );
    AuthorizationCode findUnexpiredById( String codeId );
    AuthorizationCode findUnexpiredById( String codeId, Attribute...attributes );

    List< AuthorizationCode > findAllUnexpiredByUser( User user );
    List< AuthorizationCode > findAllUnexpiredByUser( User user, Attribute...attributes );
    List< AuthorizationCode > findAllUnexpiredByClient( Client client );
    List< AuthorizationCode > findAllUnexpiredByClient( Client client, Attribute...attributes );

    boolean isUnexpiredCodeMatchesClientId( String authCode, String clientID );
}