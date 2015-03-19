package specification

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.server.Application
import tw.edu.ncu.cc.oauth.server.service.security.SecretService

@ActiveProfiles( "test" )
@WebIntegrationTest
@ContextConfiguration ( loader = SpringApplicationContextLoader.class, classes = Application.class )
public abstract class SpringSpecification extends Specification {

    @Autowired
    private SecretService secretService

    public Date laterTime() {
        return new Date( System.currentTimeMillis() + 10000 )
    }

    def String serialId( long id ) {
        secretService.encodeHashId( id )
    }

}
