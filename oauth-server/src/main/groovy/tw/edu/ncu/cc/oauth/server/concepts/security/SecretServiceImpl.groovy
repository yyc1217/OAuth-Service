package tw.edu.ncu.cc.oauth.server.concepts.security

import org.hashids.Hashids
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import tw.edu.ncu.cc.oauth.server.helper.SecretCodec
import tw.edu.ncu.cc.oauth.server.helper.StringGenerator
import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret

@Service
class SecretServiceImpl implements SecretService {

    private Hashids hashids
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
    private TextEncryptor textEncryptor

    @Autowired
    SecretServiceImpl( @Value( '${custom.oauth.security.defaultSalt}' ) String hashIdSalt,
                       @Value( '${custom.oauth.security.encrypt.password}' ) String password,
                       @Value( '${custom.oauth.security.encrypt.salt}' ) String salt ) {
        hashids = new Hashids( hashIdSalt, 16 )
        textEncryptor = Encryptors.text( password, salt )
    }

    @Override
    String encodeSecret( String rawSecret ) {
        return passwordEncoder.encode( rawSecret )
    }

    @Override
    boolean matchesSecret( String rawSecret, String encryptedSecret ) {
        return passwordEncoder.matches( rawSecret, encryptedSecret )
    }

    @Override
    String generateToken() {
        return StringGenerator.generateToken()
    }

    @Override
    String encodeSerialSecret( SerialSecret serialSecret ) {
        return SecretCodec.encode( serialSecret )
    }

    @Override
    SerialSecret decodeSerialSecret( String encodedSerialSecret ) {
        return SecretCodec.decode( encodedSerialSecret )
    }

    @Override
    String encodeHashId( long id ) {
        return hashids.encode( id )
    }

    @Override
    long decodeHashId( String hashId ) {
        long[] numbers = hashids.decode( hashId )
        return numbers.size() > 0 ? numbers[ 0 ] : -1
    }

    @Override
    String encrypt( String text ) {
        textEncryptor.encrypt( text )
    }

    @Override
    String decrypt( String encryptedText ) {
        textEncryptor.decrypt( encryptedText )
    }

}
