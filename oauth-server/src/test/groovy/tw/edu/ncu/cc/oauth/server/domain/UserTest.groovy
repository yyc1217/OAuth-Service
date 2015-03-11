package tw.edu.ncu.cc.oauth.server.domain

import specification.SpringSpecification


class UserTest extends SpringSpecification {

    def "it should have name presented"() {
        expect:
            new User().save() == null
    }

    def "it can map to exist data"() {
        given:
            def user = User.get( 1 )
        expect:
            user.name == 'ADMIN1'
    }

}
