package specification

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ContextConfiguration( [ "classpath:spring-mvc.xml" ] )
public abstract class IntegrationSpecification extends SpringSpecification {

    private WebApplicationContext webApplicationContext
    private MockMvc mockMvc

    @Autowired
    public void setWebApplicationContext( WebApplicationContext webApplicationContext ) {
        this.webApplicationContext = webApplicationContext
    }

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup( webApplicationContext ).build()
    }

    public MockMvc server() {
        return mockMvc;
    }

    protected static Object JSON( String body ) {
        return new JsonSlurper().parseText( body );
    }

    protected static Object JSON( MvcResult result ) {
        return new JsonSlurper().parseText( result.getResponse().getContentAsString() );
    }

}
