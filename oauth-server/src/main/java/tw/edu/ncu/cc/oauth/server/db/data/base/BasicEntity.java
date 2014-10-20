package tw.edu.ncu.cc.oauth.server.db.data.base;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class BasicEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Temporal( value = TemporalType.TIMESTAMP )
    private Date dateCreated;

    @Version
    @Temporal( value = TemporalType.TIMESTAMP )
    private Date dateUpdated;

    public BasicEntity() {
        dateCreated = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated( Date dateCreated ) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated( Date dateUpdated ) {
        this.dateUpdated = dateUpdated;
    }

}
