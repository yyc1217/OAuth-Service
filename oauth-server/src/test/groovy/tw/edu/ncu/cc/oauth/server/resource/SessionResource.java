package tw.edu.ncu.cc.oauth.server.resource;

import org.hibernate.Session;
import org.junit.rules.TestRule;

public interface SessionResource extends TestRule {
    public Session getSession();
}
