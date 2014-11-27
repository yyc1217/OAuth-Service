package tw.edu.ncu.cc.oauth.server.component.impl;

import org.springframework.stereotype.Component;
import tw.edu.ncu.cc.oauth.data.Permission;
import tw.edu.ncu.cc.oauth.server.component.PermissionCodec;

import java.util.HashSet;
import java.util.Set;

@Component
public class PermissionCodecImpl implements PermissionCodec {

    private static final String initPattern;
    private static final Permission[] index = Permission.values();
    private static final int length = index.length;
    private static final char yes = '1';
    private static final char no  = '0';

    static {
        StringBuilder stringBuilder = new StringBuilder( length );
        for ( int i = 0; i < length; i++ ) {
            stringBuilder.append( no );
        }
        initPattern = stringBuilder.toString();
    }

    @Override
    public boolean isValid( Set< String > permissions ) {
        try{
            for( String permission : permissions ) {
                Permission.valueOf( permission );
            }
        } catch ( IllegalArgumentException | NullPointerException ignore ) {
            return false;
        }
        return true;
    }

    @Override
    public String encode( Set< String > scope ) {
        StringBuilder stringBuilder = new StringBuilder( initPattern );
        for ( String permission : scope ) {
            stringBuilder.setCharAt( Permission.valueOf( permission ).ordinal(), yes );
        }
        return stringBuilder.toString();
    }

    @Override
    public Set< String > decode( String permission ) {
        Set< String > scope = new HashSet<>();
        for( int i = 0; i < permission.length(); i ++ ) {
            if( permission.charAt( i ) == yes ) {
                scope.add( index[ i ].toString() );
            }
        }
        return scope;
    }

}
