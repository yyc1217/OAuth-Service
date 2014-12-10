package resource;

import org.junit.rules.ExternalResource;
import org.mockserver.integration.ClientAndServer;

public class ServerResource extends ExternalResource {

    private int port;
    private ClientAndServer mockServer;

    public ServerResource( int port ) {
        this.port = port;
    }

    @Override
    protected void before() throws Throwable {
        mockServer = ClientAndServer.startClientAndServer( port );
    }

    @Override
    protected void after() {
        mockServer.stop();
    }

    public ClientAndServer mockServer() {
        return mockServer;
    }

    public int port() {
        return port;
    }


}
