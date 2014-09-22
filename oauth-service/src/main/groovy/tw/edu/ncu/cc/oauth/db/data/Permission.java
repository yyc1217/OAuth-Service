package tw.edu.ncu.cc.oauth.db.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Permission {

    @Id @GeneratedValue
    private Integer id;

    @Column( unique = true )
    private String name;

    public Permission(){}

    public Permission( String permissionName ) {
        this.name = permissionName;
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }



}
