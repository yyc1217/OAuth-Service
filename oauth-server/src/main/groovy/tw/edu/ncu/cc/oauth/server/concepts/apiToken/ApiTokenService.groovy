package tw.edu.ncu.cc.oauth.server.concepts.apiToken

import javax.persistence.metamodel.Attribute

interface ApiTokenService {

    ApiToken create( ApiToken apiToken )
    ApiToken refreshToken( ApiToken apiToken )
    ApiToken revoke( ApiToken apiToken )
    ApiToken findUnexpiredByToken( String token, Attribute...attributes )
    ApiToken findUnexpiredByToken( String token )
}