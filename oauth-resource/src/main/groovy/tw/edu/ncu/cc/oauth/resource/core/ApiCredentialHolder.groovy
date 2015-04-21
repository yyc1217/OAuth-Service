package tw.edu.ncu.cc.oauth.resource.core

import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject


class ApiCredentialHolder {

    private static Map< String, ApiTokenObject > apiTokenMap = new Hashtable<>()
    private static Map< String, AccessTokenObject > accessTokenMap = new Hashtable<>()

    static void addApiToken( String token, ApiTokenObject apiToken ) {
        apiTokenMap.put( token, apiToken )
    }

    static void removeApiToken( String token ) {
        apiTokenMap.remove( token )
    }

    static boolean containsApiToken( String token ) {
        apiTokenMap.containsKey( token )
    }

    static ApiTokenObject getApiToken( String token ) {
        apiTokenMap.get( token )
    }

    static void addAccessToken( String token,AccessTokenObject accessToken ) {
        accessTokenMap.put( token, accessToken )
    }

    static void removeAccessToken( String token ) {
        accessTokenMap.remove( token )
    }

    static boolean containsAccessToken( String token ) {
        accessTokenMap.containsKey( token )
    }

    static AccessTokenObject getAccessToken( String token ) {
        accessTokenMap.get( token )
    }

}
