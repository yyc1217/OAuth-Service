package tw.edu.ncu.cc.oauth.server.data;

import tw.edu.ncu.cc.oauth.server.entity.PermissionEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionDictionary {

    private Map< Integer, PermissionEntity > idMap;
    private Map< String, PermissionEntity > nameMap;

    public PermissionDictionary( List< PermissionEntity > permissions ) {
        idMap = new HashMap<>();
        nameMap = new HashMap<>();
        for ( PermissionEntity permission : permissions ) {
            idMap.put  ( permission.getId(),   permission );
            nameMap.put( permission.getName(), permission );
        }
    }

    public PermissionEntity getPermission( int id ) {
        return idMap.get( id );
    }

    public PermissionEntity getPermission( String name ) {
        return nameMap.get( name );
    }

}
