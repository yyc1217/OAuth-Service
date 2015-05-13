package tw.edu.ncu.cc.oauth.server.domain

import javax.persistence.*

@MappedSuperclass
class BasicEntity implements Serializable {

    @Id
    @GeneratedValue
    def Integer id

    @Version
    def Integer version = 0

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date dateCreated

    @Temporal( value = TemporalType.TIMESTAMP )
    def Date lastUpdated

    def refreshTimeStamp() {
        lastUpdated = new Date()
    }

}
