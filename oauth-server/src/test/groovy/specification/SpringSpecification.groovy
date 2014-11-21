package specification

import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

import java.text.SimpleDateFormat

@WebAppConfiguration
@ActiveProfiles( "dev" )
@ContextConfiguration( "classpath:spring-core.xml" )
public abstract class SpringSpecification extends Specification {

    protected static Date simpleDate( String dateString ) {
        return new SimpleDateFormat( "yyyy-MM-dd" ).parse( dateString );
    }

}
