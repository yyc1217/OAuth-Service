package tw.edu.ncu.cc.oauth.server.service.security

import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret


interface SecretService {

    String encodeSecret( String rawSecret )
    boolean matchesSecret( String rawSecret, String encryptedSecret )

    String generateToken()

    String encodeSerialSecret( SerialSecret serialSecret )
    SerialSecret decodeSerialSecret( String encodedSerialSecret )

    String encodeHashId( long id )
    long decodeHashId( String hashId )

}