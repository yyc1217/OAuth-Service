package tw.edu.ncu.cc.oauth.server.concepts.security

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification


class SecretServiceImplTest extends SpringSpecification {

    @Autowired
    def SecretService secretService

    def "it can encrypt string using descructive encryption"() {

        given: "a random string"
            def randomString = 'asdh%$h%&gh%^'

        when: "encrypted"
            def encryptedString = secretService.encodeSecret( randomString )

        then: "encrypted string is differnt with origin one"
            encryptedString != randomString

        and: "that encypted string can be checked if same"
            secretService.matchesSecret( randomString, encryptedString )
    }

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

}
