package tw.edu.ncu.cc.oauth.server.service;

import java.util.Set;

public interface ScopeService {
    public boolean exist( Set< String > scope );
    public String encode  ( Set< String > scope );
    public Set< String > decode( String scope );
}
