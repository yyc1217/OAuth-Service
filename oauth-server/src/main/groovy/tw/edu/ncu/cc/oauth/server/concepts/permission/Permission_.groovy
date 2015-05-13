package tw.edu.ncu.cc.oauth.server.concepts.permission

import javax.persistence.metamodel.SingularAttribute
import javax.persistence.metamodel.StaticMetamodel

@StaticMetamodel( Permission )
class Permission_ {
    public static volatile SingularAttribute< Permission, Integer > id
    public static volatile SingularAttribute< Permission, String > name
    public static volatile SingularAttribute< Permission, Date > dateCreated
    public static volatile SingularAttribute< Permission, Date > lastUpdated
}
