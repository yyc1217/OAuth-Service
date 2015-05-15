package specification

import data.DomainTestSet
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification
import tw.edu.ncu.cc.oauth.server.Application

@ActiveProfiles( "test" )
@WebAppConfiguration
@ContextConfiguration ( loader = SpringApplicationContextLoader.class, classes = Application.class )
public abstract class SpringSpecification extends Specification implements DomainTestSet {



}
