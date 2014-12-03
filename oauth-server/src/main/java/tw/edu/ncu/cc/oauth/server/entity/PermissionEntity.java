package tw.edu.ncu.cc.oauth.server.entity;

import tw.edu.ncu.cc.oauth.server.entity.base.BasicEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "permission" )
public class PermissionEntity extends BasicEntity {

    private String name;

    @Basic
    @Column( name = "name", unique = true )
    public String getName() {
        return name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }

}
