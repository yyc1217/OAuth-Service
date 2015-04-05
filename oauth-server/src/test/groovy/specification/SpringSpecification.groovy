package specification

import data.DomainTestSet
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.server.Application

@ActiveProfiles( "test" )
@WebIntegrationTest
@ContextConfiguration ( loader = SpringApplicationContextLoader.class, classes = Application.class )
public abstract class SpringSpecification extends Specification implements DomainTestSet {



}
