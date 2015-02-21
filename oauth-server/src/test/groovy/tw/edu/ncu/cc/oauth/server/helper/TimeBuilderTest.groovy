package tw.edu.ncu.cc.oauth.server.helper

import specification.SpringSpecification
import tw.edu.ncu.cc.oauth.server.helper.data.TimeUnit


class TimeBuilderTest extends SpringSpecification {

    def "it can build date from specified date"() {
        given:
            def sourceDate = timeNow();
        when:
            def targetDate = TimeBuilder
                    .of( sourceDate )
                    .buildDate()
        then:
            targetDate.time == sourceDate.time
    }

    def "it can build date after specified time"() {
        given:
            def sourceDate = timeNow();
        when:
            def targetDate = TimeBuilder
                    .of( sourceDate )
                    .after( 1, TimeUnit.DAY )
                    .buildDate()
        then:
            targetDate.time == ( sourceDate.time + 1 * 1000 * 60 * 60 * 24 )
    }

    def "it can build date before specified time"() {
        given:
            def sourceDate = timeNow();
        when:
            def targetDate = TimeBuilder
                    .of( sourceDate )
                    .before( 1, TimeUnit.HOUR )
                    .buildDate()
        then:
            targetDate.time == ( sourceDate.time - 1 * 1000 * 60 * 60 )
    }


}
