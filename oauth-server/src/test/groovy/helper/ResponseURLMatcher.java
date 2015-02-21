package helper;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class ResponseURLMatcher implements ResultMatcher {

    private Map< String, String > expectedParams;

    protected ResponseURLMatcher() {
        expectedParams = new HashMap<>();
    }

    public ResponseURLMatcher param( String name, String expectedValue ) {
        expectedParams.put( name, expectedValue );
        return this;
    }

    @Override
    public void match( MvcResult result ) throws Exception {
        if( expectedParams.size() != 0 ) {
            Map< String, String > actualParams = getActualParams( result );

            for ( Map.Entry< String, String > param : expectedParams.entrySet() ) {
                assertEquals("response url param", param.getValue(), actualParams.get( param.getKey() ));
            }
        }
    }

    private Map< String, String > getActualParams( MvcResult result ) {
        Map< String, String > actualParams = new HashMap<>();
        try {
            String url = result.getResponse().getRedirectedUrl();
            String[] params = url.substring( url.indexOf( '?' ) + 1 ).split( "&" );
            for( String param : params ) {
                String[] entry = param.split( "=" );
                actualParams.put( entry[0], entry[1] );
            }
            return actualParams;
        } catch ( StringIndexOutOfBoundsException ignore ) {
            return actualParams;
        } catch ( ArrayIndexOutOfBoundsException e ) {
            throw new RuntimeException( "url is invalid", e );
        }
    }

}
