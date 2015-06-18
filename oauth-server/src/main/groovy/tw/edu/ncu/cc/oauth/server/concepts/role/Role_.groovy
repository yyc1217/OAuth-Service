package tw.edu.ncu.cc.oauth.server.concepts.role

import javax.persistence.metamodel.SingularAttribute
import javax.persistence.metamodel.StaticMetamodel

@StaticMetamodel( Role )
class Role_ {
    public static volatile SingularAttribute< Role, Integer > id
    public static volatile SingularAttribute< Role, String > name
    public static volatile SingularAttribute< Role, Date > dateCreated
    public static volatile SingularAttribute< Role, Date > lastUpdated
}
