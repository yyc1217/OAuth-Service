package tw.edu.ncu.cc.oauth.server.entity.base;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class BasicEntity {

    private Integer id;
    private Date dateCreated;
    private Date dateUpdated;

    public BasicEntity() {
        dateCreated = new Date();
    }

    @Id
    @GeneratedValue
    @Column( name = "ID" )
    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    @Column( name = "DATE_CREATED" )
    @Temporal( value = TemporalType.TIMESTAMP )
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated( Date dateCreated ) {
        this.dateCreated = dateCreated;
    }

    @Version
    @Column( name = "DATE_UPDATED" )
    @Temporal( value = TemporalType.TIMESTAMP )
    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated( Date dateUpdated ) {
        this.dateUpdated = dateUpdated;
    }

}
