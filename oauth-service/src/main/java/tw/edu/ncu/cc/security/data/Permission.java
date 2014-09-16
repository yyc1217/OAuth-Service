package tw.edu.ncu.cc.security.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Permission {

    @Id @GeneratedValue
    private Integer id;
    private String name;

    public Permission() {

    }

    public Permission( String name ) {
        this.name = name;
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

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals( Object o ) {

        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Permission that = ( Permission ) o;

        return !( name != null ? !name.equals( that.name ) : that.name != null );
    }
}
