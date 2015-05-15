package tw.edu.ncu.cc.oauth.server.domain

import javax.persistence.metamodel.SingularAttribute
import javax.persistence.metamodel.StaticMetamodel

@StaticMetamodel( BasicEntity )
class BasicEntity_ {
    public static volatile SingularAttribute< BasicEntity, Integer > id
    public static volatile SingularAttribute< BasicEntity, Integer > version
    public static volatile SingularAttribute< BasicEntity, Date > dateCreated
    public static volatile SingularAttribute< BasicEntity, Date > lastUpdated
}
