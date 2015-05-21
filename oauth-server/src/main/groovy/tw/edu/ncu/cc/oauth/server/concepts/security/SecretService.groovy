package tw.edu.ncu.cc.oauth.server.concepts.security

import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret


interface SecretService {

    String generateToken()

    String encodeSerialSecret( SerialSecret serialSecret )
    SerialSecret decodeSerialSecret( String encodedSerialSecret )

    String encodeHashId( long id )
    long decodeHashId( String hashId )

    String encrypt( String text )
    String decrypt( String encryptedText )
    boolean matches( String text, String encryptedText )

}