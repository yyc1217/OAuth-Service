package tw.edu.ncu.cc.oauth.server.entity.base;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ScopeEntity extends BasicEntity {

    private String scope;

    @Basic
    @Column( name = "scope" , nullable = false )
    public String getScope() {
        return scope;
    }

    public void setScope( String permission ) {
        this.scope = permission;
    }

}
