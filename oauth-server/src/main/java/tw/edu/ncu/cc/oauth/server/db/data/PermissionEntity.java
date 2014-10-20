package tw.edu.ncu.cc.oauth.server.db.data;

import tw.edu.ncu.cc.oauth.server.db.data.base.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PermissionEntity extends BasicEntity {

    @Column( unique = true )
    private String name;

    public PermissionEntity(){}

    public PermissionEntity( String permissionName ) {
        this.name = permissionName;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

}
