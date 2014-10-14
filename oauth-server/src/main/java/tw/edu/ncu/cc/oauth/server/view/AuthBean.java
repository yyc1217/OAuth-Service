package tw.edu.ncu.cc.oauth.server.view;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.Set;

@SuppressWarnings( "unchecked" )
public class AuthBean {

    private HttpSession httpSession;

    private String state;
    private String clientName;
    private String clientURL;

    @Inject
    public AuthBean( HttpSession httpSession ) {
        this.httpSession = httpSession;
        this.httpSession.setMaxInactiveInterval( 180 );
    }

    public Set<String>  getScope() {
        return (Set<String>) httpSession.getAttribute( "scope" );
    }

    public void setScope( Set<String>  scope ) {
        httpSession.setAttribute( "scope", scope );
    }

    public String getClientID() {
        return httpSession.getAttribute( "clientID" ).toString();
    }

    public void setClientID( String clientID ) {
       httpSession.setAttribute( "clientID", clientID );
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName( String clientName ) {
        this.clientName = clientName;
    }

    public String getPortalID() {
        return httpSession.getAttribute( "portalID" ).toString();
    }

    public void setPortalID( String portalID ) {
        httpSession.setAttribute( "portalID", portalID );
    }

    public String getState() {
        return state;
    }

    public void setState( String state ) {
        this.state = state;
    }

    public String getClientURL() {
        return clientURL;
    }

    public void setClientURL( String clientURL ) {
        this.clientURL = clientURL;
    }
}
