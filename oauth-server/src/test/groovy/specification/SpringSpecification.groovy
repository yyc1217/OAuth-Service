package specification

import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@WebAppConfiguration
@ActiveProfiles( "dev" )
@ContextConfiguration( [ "classpath:spring-core.xml", "classpath:spring-mvc.xml" ] )
public abstract class SpringSpecification extends Specification {


}
