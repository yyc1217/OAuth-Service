package tw.edu.ncu.cc.oauth.server.entity.base;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PermissionEntity extends BasicEntity {

    private String permission;

    @Basic
    @Column( name = "permission" , nullable = false )
    public String getPermission() {
        return permission;
    }

    public void setPermission( String permission ) {
        this.permission = permission;
    }

}
