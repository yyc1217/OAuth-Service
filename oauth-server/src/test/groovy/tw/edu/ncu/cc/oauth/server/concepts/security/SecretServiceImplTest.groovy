package tw.edu.ncu.cc.oauth.server.concepts.security

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification

class SecretServiceImplTest extends SpringSpecification {

    @Autowired
    def SecretService secretService

    def "it can encode long into string hash id"() {

        given: "a random long between 0 ~ 9007199254740992L"
            def randomLong = Math.abs( new Random().nextLong() ) % 9007199254740992L

        when: "encode into hash id"
            def hashId = secretService.encodeHashId( randomLong )

        then: "hash id is different with origin id"
            hashId != randomLong

        and: "hash id should have length >= 16"
            hashId.length() >= 16

        and: "hash id can be decoded"
            secretService.decodeHashId( hashId ) == randomLong

    }

    def "it can encrypt/decrypt text"() {
        given: "a text to be encrypted"
            def text = "abc123"
        when: "encrypt text"
            def encryptedText = secretService.encrypt( text )
        and: "decrypt the encrypted text"
            def decryptedText = secretService.decrypt( encryptedText )
        then: "decrypted text should be same as origin text"
            decryptedText == text
        and: "text and its encrypted version is matched"
            secretService.matches( text, encryptedText )
    }

}
