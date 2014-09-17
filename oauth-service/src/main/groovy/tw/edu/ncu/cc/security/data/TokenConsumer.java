package tw.edu.ncu.cc.security.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TokenConsumer {

    @Id
    @GeneratedValue
    private Integer id;

    @Column( unique = true )
    private String  ip;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

}
