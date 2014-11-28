package tw.edu.ncu.cc.oauth.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.service.PermissionDictionaryService


class PermissionDictionaryServiceImplTest extends SpringSpecification {

    @Autowired
    private PermissionDictionaryService dictionaryService

    def "it can generate PermissionDictionary"() {
        when:
            def dictionary = dictionaryService.generateDictionary()
        then:
            dictionary.getPermission( 1 ) != null
            dictionary.getPermission( 2 ) != null
    }

}
