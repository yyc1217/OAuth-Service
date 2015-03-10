package tw.edu.ncu.cc.oauth.server.helper.data

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

public class EditableRequest extends HttpServletRequestWrapper {

    private HttpServletRequest servletRequest;
    private Map< String, String > params = new HashMap<>();

    public EditableRequest( HttpServletRequest request ) {
        super( request );
        this.servletRequest = request;
    }

    public String getParameter( String name ) {
        if ( params.get( name ) != null ) {
            return params.get( name );
        } else {
            return servletRequest.getParameter( name );
        }
    }

    public EditableRequest setParameter( String name, String value ) {
        params.put( name, value );
        return this;
    }

}
