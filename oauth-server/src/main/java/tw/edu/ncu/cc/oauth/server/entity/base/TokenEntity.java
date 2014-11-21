package tw.edu.ncu.cc.oauth.server.entity.base;

import tw.edu.ncu.cc.oauth.data.Permission;
import tw.edu.ncu.cc.oauth.server.rule.PermissionRule;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class TokenEntity extends BasicEntity {

    private String permission = PermissionRule.INIT_PATTERN;

    public boolean hasPermissions( Permission... permissions ) {
        for( Permission p : permissions ) {
            if( permission.charAt( p.ordinal() ) == PermissionRule.NO ) {
                return false;
            }
        }
        return true;
    }

    public void setPermissions( Permission...permissions ) {
        StringBuilder stringBuilder = new StringBuilder( permission );
        for( Permission p : permissions ) {
            stringBuilder.setCharAt( p.ordinal(), PermissionRule.YES );
        }
        permission = stringBuilder.toString();
    }

    @Basic
    @Column( name = "permission" )
    public String getPermission() {
        return permission;
    }

    public void setPermission( String permission ) {
        this.permission = permission;
    }

}
