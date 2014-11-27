package tw.edu.ncu.cc.oauth.server.component;

import java.util.Set;

public interface PermissionCodec {
    public boolean isValid( Set< String > permissions );
    public String encode( Set< String > scope ) ;
    public Set< String > decode( String permission );
}
